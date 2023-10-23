package com.jalalkun.movieapp.repository

import com.jalalkun.movieapp.db.FavoriteMovieDao
import com.jalalkun.movieapp.db.FavoriteMovieEntity
import com.jalalkun.movieapp.helper.MoshiHelper
import com.jalalkun.movieapp.models.movie_detail.MovieDetail
import com.jalalkun.movieapp.state.ResultState
import com.pluto.plugins.logger.PlutoLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.Date

class FavoriteMovieRepositoryImpl(
    private val favoriteMovieDao: FavoriteMovieDao,
    private val apiRepository: ApiRepository
) : FavoriteMovieRepository {
    override fun insert(movie: MovieDetail): Long {
        val entity = FavoriteMovieEntity(
            id = 0,
            movieId = movie.id,
            movieData = MoshiHelper.convertToJson(movie, MovieDetail::class.java),
            createdAt = Date()
        )
        return favoriteMovieDao.insert(entity)
    }

    override fun isMovieFavorite(id: Int): Boolean {
        val a = favoriteMovieDao.isMovieExist(id) > 0
        PlutoLog.e("FavoriteMovieRepositoryImpl", "isMovieFavorite $a")
        return a
    }

    override fun deleteByMovieId(id: Int): Boolean {
        return favoriteMovieDao.deleteByMovieId(id) > 0
    }

    override fun getMovie(id: Int): Flow<ResultState> = flow {
        emit(ResultState.Loading)
        try {
            val m = favoriteMovieDao.getMovie(id)
            val a = m?.movieData?.let { MoshiHelper.convertToObject(it, MovieDetail::class.java) }
            if (a != null) emit(ResultState.Success(a))
            else emit(ResultState.Success(apiRepository.getMovieDetail(id)))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }
        .flowOn(Dispatchers.IO)

    override fun getListFavoriteMovie(offset: Int): Flow<ResultState> = flow {
        emit(ResultState.Loading)
        try {
            val a = favoriteMovieDao.getListFavoriteMovie(limit = 10, offset = offset)
            emit(ResultState.Success(a.map {
                MoshiHelper.convertToObject(
                    it.movieData,
                    MovieDetail::class.java
                )
            }))
        } catch (e: Exception) {
            emit(ResultState.Error(e))
        }
    }.flowOn(Dispatchers.IO)
}