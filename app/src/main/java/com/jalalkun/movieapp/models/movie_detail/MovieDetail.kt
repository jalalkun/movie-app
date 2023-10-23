package com.jalalkun.movieapp.models.movie_detail

import com.jalalkun.movieapp.models.Genre
import com.jalalkun.movieapp.models.Movie

data class MovieDetail(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val belongs_to_collection: Any? = null,
    val budget: Int = 0,
    val genres: List<Genre> = listOf(),
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val production_companies: List<ProductionCompany> = listOf(),
    val production_countries: List<ProductionCountry> = listOf(),
    val release_date: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val spoken_languages: List<SpokenLanguage> = listOf(),
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
) {
    companion object {
        fun MovieDetail.toMovie(): Movie {
            return Movie(
                adult = adult,
                backdrop_path = backdrop_path,
                genre_ids = genres.map { it.id },
                id = id,
                original_language = original_language,
                original_title = original_title,
                overview = overview,
                popularity = popularity,
                poster_path = poster_path,
                release_date = release_date,
                title = title,
                video = video,
                vote_average = vote_average,
                vote_count = vote_count
            )
        }
    }
}