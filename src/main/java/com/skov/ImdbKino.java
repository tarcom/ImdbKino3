package com.skov;

import java.io.IOException;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ImdbKino {

    TreeSet<String> sortedMovies = new TreeSet<String>();

    public static void main(String[] args) throws InterruptedException, IOException {
        System.out.println("Welcome to ImdbKino");
        System.out.println(new ImdbKino().execute());
    }

    public String execute() throws InterruptedException, IOException {
        StringBuffer htmlOutputStringBuffer = new StringBuffer();

        String kinoUrl = "https://kino.dk/aktuelle-film";
        htmlOutputStringBuffer.append("I will collect movies from " + kinoUrl + "<br>\n<br>\n");
        long startTime = System.currentTimeMillis();

        Elements alleFilm = Jsoup.connect(kinoUrl).get().select("div.movies-grid__movie-list");

        htmlOutputStringBuffer.append("I found " + alleFilm.size() + " movies in " + kinoUrl + "<br>\n<br>\n");

        ExecutorService es = Executors.newCachedThreadPool();
        for (final Element film : alleFilm) {
            String kinoMovieTitle = film.select("div.movies-grid__movie-title").text();
            String kinoFilmUrl = "https://kino.dk" + film.select("a.button.button--ghost").attr("href");

            es.execute(new Thread(kinoMovieTitle) {
                public void run() {
                    System.out.println("Thread Running... name=" + getName());
                    FetchSingleMovie.getSingleMovie(sortedMovies, kinoMovieTitle, kinoFilmUrl);
                }
            });
        }

        es.shutdown();

        boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);


        System.out.println("");
        System.out.println("");
        for (String sortedMovie : sortedMovies.descendingSet()) {
            //System.out.println(sortedMovie);
            htmlOutputStringBuffer.append(sortedMovie + " <br>\n");
        }

        htmlOutputStringBuffer.append("<br>\nAll movies have been collected.");
        htmlOutputStringBuffer.append(
                "<br>\nI started out with " + alleFilm.size() + " film, and have collected IMDB info from " + sortedMovies.size() + " movies. <br>\n");
        htmlOutputStringBuffer.append("Time used: " + (System.currentTimeMillis() - startTime) / 1000l + " seconds.<br>\n");
        return htmlOutputStringBuffer.toString();

    }

}
