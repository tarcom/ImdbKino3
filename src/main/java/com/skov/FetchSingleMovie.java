package com.skov;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Place description here.
 *
 * @author ALSK@nykredit.dk
 */

public class FetchSingleMovie {

    public static void main(String[] args) {
        TreeSet<String> sortedMovies = new TreeSet<String>();
        getSingleMovie(sortedMovies, "The Monkey", "https://kino.dk/film/monkey");
    }

    public static void getSingleMovie(TreeSet<String> sortedMovies, String kinoMovieTitle, String kinoFilmUrl) {
        String imdbUrl = null;
        try {

            Document kinoDoc = Jsoup.connect(kinoFilmUrl).get();
            imdbUrl = Jsoup.connect(kinoFilmUrl).get().select(".logo.movie-view__imdb-logo a").first().attr("href");

            String censur = kinoDoc.select("div.movie-view__censur-wrapper").first().select("img").first().attr("alt").replace("Censur", "").trim();
            //kinoDoc.select("div.movie-view__censur-wrapper").first().select(".hovertext").first().attr("data-hover")


            Document document = Jsoup.connect(imdbUrl).get();
            String score = document.select(".sc-d541859f-1.imUuxf").first().text();
            String votes = document.select(".sc-d541859f-3.dwhNqC").first().text();

            System.out.println("Title: " + kinoMovieTitle + ", kinoFilmUrl: " + kinoFilmUrl + ", imdbUrl: " + imdbUrl + ", score: " + score + ", votes: " + votes + ", censur: " + censur);


            String movieHit = "<tr><td>" + score + "</td><td><a href='" + imdbUrl + "'>link</a></td><td><b>" + kinoMovieTitle + "</b></td><td>" + votes + "</td><td>" + censur + "</td></tr>";
            sortedMovies.add(movieHit);
            System.out.println(movieHit);
        } catch (IOException e) {
            System.err.println("Error: " + kinoMovieTitle + ", " + kinoFilmUrl + ", " + imdbUrl);
            e.printStackTrace();
            ImdbKino.errors.append("Error: " + kinoMovieTitle + ", <a href='" + kinoFilmUrl + "'>" + kinoFilmUrl + "</a>, " + imdbUrl + ", e=" + e.getMessage() + "<br>\n");
        }

    }
}
