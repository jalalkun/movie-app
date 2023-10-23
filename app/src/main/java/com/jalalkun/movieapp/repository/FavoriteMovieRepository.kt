package com.jalalkun.movieapp.repository

import com.jalalkun.movieapp.models.movie_detail.MovieDetail
import com.jalalkun.movieapp.state.ResultState
import kotlinx.coroutines.flow.Flow

interface FavoriteMovieRepository {
    fun insert(movie: MovieDetail): Long
    fun isMovieFavorite(id: Int): Boolean
    fun deleteByMovieId(id: Int): Boolean

    fun getMovie(id: Int): Flow<ResultState>

    fun getListFavoriteMovie(offset: Int): Flow<ResultState>
}