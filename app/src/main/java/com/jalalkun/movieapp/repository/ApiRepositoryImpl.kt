package com.jalalkun.movieapp.repository

import com.jalalkun.movieapp.models.ResponseVideo
import com.jalalkun.movieapp.network.ApiService
import com.jalalkun.movieapp.state.ResultState
import com.pluto.plugins.logger.PlutoLog
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

class ApiRepositoryImpl(
    private val apiService: ApiService
) : ApiRepository {
    override fun getGenre(): Flow<ResultState> = flow {
        emit(ResultState.Loading)
        try {
            val response = apiService.getMovieGenre()
            emit(ResultState.Success(response.genres))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getListMovie(genre: String, page: Int): Flow<ResultState> = flow {
        emit(ResultState.Loading)
        try {
            val query = hashMapOf<String, String>()
            query["include_adult"] = "false"
            query["include_video"] = "false"
            query["language"] = "en-US"
            query["page"] = page.toString()
            query["sort_by"] = "popularity.desc"
            query["with_genres"] = genre
            emit(ResultState.Success(apiService.getMovieList(query)))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override fun getMovieDetail(id: Int): Flow<ResultState> = flow {
        emit(ResultState.Loading)
        try {
            emit(ResultState.Success(apiService.getMovieDetail(id)))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieVideo(id: Int): ResponseVideo {
        val result = runBlocking(Dispatchers.IO) {
            val def: Deferred<ResponseVideo> = async {
                try {
                    apiService.getMovieVideo(id)
                } catch (e: Exception) {
                    PlutoLog.e("getMovieVideo", "e ${e.message}")
                    ResponseVideo(
                        id = id,
                        results = listOf()
                    )
                }
            }
            return@runBlocking def
        }
        return result.await()
    }

    override fun getMovieReview(id: Int, page: Int): Flow<ResultState> = flow<ResultState> {
        emit(ResultState.Idle)
        try {
            val query = hashMapOf<String, String>()
            query["page"] = page.toString()
            query["language"] = "en-US"
            emit(ResultState.Success(apiService.getMovieReview(
                id, query
            )))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }

    }.flowOn(Dispatchers.IO)
}