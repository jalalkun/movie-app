package com.jalalkun.movieapp.ui.favorite_movie

sealed class FavoriteMovieEvent {
    data object GetFavoriteMovie: FavoriteMovieEvent()
    data object DissmissError : FavoriteMovieEvent()
}