package com.jalalkun.movieapp.modules

import com.jalalkun.movieapp.repository.ApiRepository
import com.jalalkun.movieapp.repository.ApiRepositoryImpl
import com.jalalkun.movieapp.repository.FavoriteMovieRepository
import com.jalalkun.movieapp.repository.FavoriteMovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ApiRepository> { ApiRepositoryImpl(get()) }
    single<FavoriteMovieRepository> { FavoriteMovieRepositoryImpl(get(), get()) }
}