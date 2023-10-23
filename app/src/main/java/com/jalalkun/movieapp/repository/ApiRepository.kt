package com.jalalkun.movieapp.repository

import com.jalalkun.movieapp.models.ResponseVideo
import com.jalalkun.movieapp.state.ResultState
import kotlinx.coroutines.flow.Flow

interface ApiRepository {
    fun getGenre(): Flow<ResultState>

    fun getListMovie(genre: String, page: Int): Flow<ResultState>

    fun getMovieDetail(id: Int): Flow<ResultState>

    suspend fun getMovieVideo(id: Int): ResponseVideo

    fun getMovieReview(
        id: Int,
        page: Int
    ): Flow<ResultState>
}