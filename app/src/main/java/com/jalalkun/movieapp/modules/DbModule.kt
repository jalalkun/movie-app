package com.jalalkun.movieapp.modules

import androidx.room.Room
import com.jalalkun.movieapp.db.FavoriteMovieEntity
import com.jalalkun.movieapp.db.MovieDb
import com.jalalkun.movieapp.helper.movieFavoriteTable
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MovieDb::class.java,
            FavoriteMovieEntity::class.simpleName
        )
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<MovieDb>().getFavoriteMovieDao()
    }
}