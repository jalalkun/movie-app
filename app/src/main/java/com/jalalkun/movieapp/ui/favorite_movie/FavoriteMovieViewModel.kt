package com.jalalkun.movieapp.ui.favorite_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalalkun.movieapp.models.movie_detail.MovieDetail
import com.jalalkun.movieapp.repository.FavoriteMovieRepository
import com.jalalkun.movieapp.state.ResultState
import com.pluto.plugins.logger.PlutoLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(
    private val favoriteMovieRepository: FavoriteMovieRepository
): ViewModel() {
    private val _moviesState: MutableStateFlow<ResultState> = MutableStateFlow(ResultState.Idle)
    val moviesState = _moviesState.asStateFlow()

    private val _movieList =MutableStateFlow(mutableListOf<MovieDetail>())
    val movieList = _movieList.asStateFlow()
    init {
        PlutoLog.e("init", "init")
        onEvent(FavoriteMovieEvent.GetFavoriteMovie)

    }
    fun init(){
        viewModelScope.launch {
            _movieList.value.clear()
            _moviesState.value = ResultState.Idle
        }
    }
    fun onEvent(event: FavoriteMovieEvent) {
        PlutoLog.e("onEvent", event.toString())
        viewModelScope.launch {
            when(event){
                is FavoriteMovieEvent.GetFavoriteMovie -> {
                    getFavoriteMovie()
                }
                is FavoriteMovieEvent.DissmissError -> {
                    _moviesState.emit(ResultState.Idle)
                }
            }
        }
    }

    fun setMovieList(list: List<*>){
        PlutoLog.e("setMovieList", "")
        viewModelScope.launch {
            list.forEach {
                if (it is MovieDetail && !_movieList.value.contains(it)){
                    _movieList.value.add(it)
                }
            }
            _moviesState.emit(ResultState.Idle)
            PlutoLog.e("setMovieList", "list size ${_movieList.value.size}")
        }
    }

    private fun getFavoriteMovie() {
        viewModelScope.launch {
            favoriteMovieRepository.getListFavoriteMovie(if (movieList.value.isNotEmpty())_movieList.value.last().id else 0)
                .collect{
                    PlutoLog.e("getFavoriteMovie", "result $it")
                    _moviesState.emit(it)
                }
        }
    }


}