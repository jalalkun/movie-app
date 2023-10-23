package com.jalalkun.movieapp.network

import com.jalalkun.movieapp.BuildConfig
import com.jalalkun.movieapp.models.GenreResponse
import com.jalalkun.movieapp.models.MovieResponse
import com.jalalkun.movieapp.models.ResponseVideo
import com.jalalkun.movieapp.models.ReviewResponse
import com.jalalkun.movieapp.models.movie_detail.MovieDetail
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiService {
    @GET(BuildConfig.MOVIE_GENRE)
    suspend fun getMovieGenre(): GenreResponse

    @GET(BuildConfig.MOVIE_LIST)
    suspend fun getMovieList(
        @QueryMap query: HashMap<String, String>
    ): MovieResponse

    @GET(BuildConfig.MOVIE_DETAIL)
    suspend fun getMovieDetail(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US"
    ): MovieDetail

    @GET(BuildConfig.MOVIE_VIDEO)
    suspend fun getMovieVideo(
        @Path("movie_id") id: Int
    ): ResponseVideo

    @GET(BuildConfig.MOVIE_REVIEW)
    suspend fun getMovieReview(
        @Path("movie_id") id: Int,
        @QueryMap query: HashMap<String, String>
    ): ReviewResponse
}