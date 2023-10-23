package com.jalalkun.movieapp.navigation

import androidx.navigation.NavHostController

object MovieNav {
    private const val basePath = "movie_app/"
    private const val homePath = "home_screen/"
    private const val listMoviePath = "list_movie/"
    private const val detailMovie = "detail_movie/"
    private const val favoriteMoviePath = "favorite_movie"

    /**
     * Params
     */
    private const val paramsGenre = "{genre_id}"
    private const val paramsIdMovie = "{movie_id}"

    fun homePath() = "$basePath$homePath"

    fun listMoviePath() = "$basePath$listMoviePath$paramsGenre"

    fun favoriteMoviePath() = "$basePath$favoriteMoviePath"

    fun NavHostController.toListMovieScreen(genreId: String){
        navigate(listMoviePath().replace(paramsGenre, genreId))
    }

    fun getParamGenre() = paramsGenre.replace("{","").replace("}","")

    fun detailMoviePath() = "$basePath$detailMovie$paramsIdMovie"

    fun NavHostController.toDetailMovieScreen(id: String){
        navigate(detailMoviePath().replace(paramsIdMovie, id))
    }

    fun getParamId() = paramsIdMovie.replace("{","").replace("}","")

    fun NavHostController.toFavoriteMovieScreen(){
        navigate(favoriteMoviePath())
    }
}