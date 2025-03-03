package com.skov;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class ImdbKino_singleThread {

    public static void main(String[] args) throws Exception {
        new ImdbKino_singleThread().execute();
    }

    public void execute() throws Exception {


        String kinoUrl = "https://kino.dk/aktuelle-film";

        Elements alleFilm = Jsoup.connect(kinoUrl).get().select("div.movies-grid__movie-list");

        System.out.println("I found " + alleFilm.size() + " movies in " + kinoUrl);

        for (final Element film : alleFilm) {
            String kinoMovieTitle = film.select("div.movies-grid__movie-title").text();
            String kinoFilmUrl = "https://kino.dk" + film.select("a.button.button--ghost").attr("href");

            String imdbUrl = Jsoup.connect(kinoFilmUrl).get().select(".logo.movie-view__imdb-logo a").first().attr("href");

            Document document = Jsoup.connect(imdbUrl).get();
            String score = document.select(".sc-d541859f-1.imUuxf").first().text();
            String votes = document.select(".sc-d541859f-3.dwhNqC").first().text();

            System.out.println("Title: " + kinoMovieTitle + ", kinoFilmUrl: " + kinoFilmUrl + ", imdbUrl: " + imdbUrl + ", score: " + score + ", votes: " + votes);


        }
    }

}
