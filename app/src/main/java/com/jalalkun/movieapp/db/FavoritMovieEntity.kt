package com.jalalkun.movieapp.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jalalkun.movieapp.helper.movieFavoriteTable
import java.util.Date

@Entity
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo("movie_data") val movieData: String,
    @ColumnInfo("movie_id") val movieId: Int,
    @ColumnInfo(name = "created_at") val createdAt: Date?,
)