package com.jalalkun.movieapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(entities = [FavoriteMovieEntity::class], version = 3)
@TypeConverters(DateConverter::class)
abstract class MovieDb : RoomDatabase(){
    abstract fun getFavoriteMovieDao(): FavoriteMovieDao
}