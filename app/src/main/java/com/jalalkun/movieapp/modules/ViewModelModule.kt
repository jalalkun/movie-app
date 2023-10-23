package com.jalalkun.movieapp.modules

import com.jalalkun.movieapp.ui.MovieAppViewModel
import com.jalalkun.movieapp.ui.favorite_movie.FavoriteMovieViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { MovieAppViewModel(get(), get()) }
    single { FavoriteMovieViewModel(get()) }
}