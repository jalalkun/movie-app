package com.jalalkun.movieapp.ui

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.jalalkun.movieapp.models.Genre
import com.jalalkun.movieapp.models.Movie
import com.jalalkun.movieapp.models.MovieResponse
import com.jalalkun.movieapp.models.Review
import com.jalalkun.movieapp.models.ReviewResponse
import com.jalalkun.movieapp.models.Youtube
import com.jalalkun.movieapp.models.movie_detail.MovieDetail
import com.jalalkun.movieapp.repository.ApiRepository
import com.jalalkun.movieapp.repository.FavoriteMovieRepository
import com.jalalkun.movieapp.state.ResultState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pluto.plugins.logger.PlutoLog
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieAppViewModel(
    private val repository: ApiRepository,
    private val favoriteMovieRepository: FavoriteMovieRepository
) : ViewModel() {

    private val _genreState = MutableStateFlow<ResultState>(ResultState.Idle)
    val genreState = _genreState.asStateFlow()

    fun getGenre() {
        viewModelScope.launch {
            repository.getGenre().collect {
                _genreState.emit(it)
            }
        }
    }

    fun dismissGenre() {
        viewModelScope.launch {
            _genreState.emit(ResultState.Idle)
        }
    }

    val listGenre = MutableStateFlow(mutableListOf<Genre>())

    fun setListGenre(list: List<*>) {
        list.forEach { data ->
            if (data is Genre && !listGenre.value.contains(data)) {
                listGenre.value.add(data)
            }
        }
    }

    private val _listMovieState = MutableStateFlow<ResultState>(ResultState.Idle)
    val listMovieState = _listMovieState.asStateFlow()
    private var page = 0
    private var maxPage = 1
    fun getListMovie(genre: String) {
        if (page != 0) initMovie()
        viewModelScope.launch {
            if (page < maxPage) {
                page++
                repository.getListMovie(
                    genre, page
                ).collect {
                    _listMovieState.emit(it)
                }
            }
        }
    }

    fun dismissMovieState() {
        viewModelScope.launch {
            _listMovieState.emit(ResultState.Idle)
        }
    }

    val listMovie = MutableStateFlow(mutableListOf<Movie>())
    fun setListMovie(response: MovieResponse) {
        maxPage = response.total_pages
        page = response.page
        listMovie.value.addAll(response.results)
    }

    fun initMovie() {
        viewModelScope.launch {
            page = 0
            maxPage = 1
            listMovie.value.clear()
        }
    }

    /**
     * detail movie
     */
    private val _detailMovieState = MutableStateFlow<ResultState>(ResultState.Idle)
    val detailMovieState = _detailMovieState.asStateFlow()
    fun getDetailMovie(id: Int) {
        viewModelScope.launch {
            repository.getMovieDetail(id)
                .collect {
                    _detailMovieState.emit(it)
                }
        }
    }

    fun dismissMovieDetail() {
        viewModelScope.launch {
            _detailMovieState.emit(ResultState.Idle)
        }
    }

    var movieDetail = mutableStateOf<MovieDetail?>(null)
    fun setMovieDetail(movieDetail: MovieDetail) {
        PlutoLog.e("setMovieDetail", "setMovieDetail")
        this.movieDetail.value = movieDetail
        isMovieFavourite(movieDetail.id)
    }

    val listVideo = MutableStateFlow(mutableListOf<Youtube>())

    fun getVideos(id: Int, io: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(io) {
            val a = repository.getMovieVideo(id)
            PlutoLog.e("getVideos", Gson().toJson(a))
            listVideo.value.clear()
            listVideo.value.addAll(a.results)
        }
    }


    private val _listReviewState = MutableStateFlow<ResultState>(ResultState.Idle)
    val listReviewState = _listReviewState.asStateFlow()
    private var pageReview = 0
    private var maxPageReview = 1

    fun getMovieReview(id: Int) {
        viewModelScope.launch {
            if (pageReview < maxPageReview) {
                pageReview++
                repository.getMovieReview(id, pageReview)
                    .collect {
                        _listReviewState.emit(it)
                    }
            }
        }
    }

    fun dismissReview() {
        viewModelScope.launch {
            _listReviewState.emit(ResultState.Idle)
        }
    }

    val listReview = MutableStateFlow(mutableListOf<Review>())
    fun setReview(data: ReviewResponse) {
        pageReview = data.page
        maxPageReview = data.total_pages
        data.results.forEach {
            if (!listReview.value.contains(it)) {
                listReview.value.add(it)
            }
        }
    }

    private var youTubePlayerView: YouTubePlayerView? = null

    fun getYoutubePlayerView(context: Context): YouTubePlayerView? {
        youTubePlayerView?.release()
        youTubePlayerView = null
        youTubePlayerView = YouTubePlayerView(context)
        youTubePlayerView?.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    setYoutubePlayer(youTubePlayer)
                    if (listVideo.value.isNotEmpty()) playVideo(
                        listVideo.value.random().key
                    )
                }
            }
        )
        return youTubePlayerView
    }

    private var youTubePlayer: YouTubePlayer? = null
    fun setYoutubePlayer(youTubePlayer: YouTubePlayer) {
        this.youTubePlayer = youTubePlayer
    }

    fun playVideo(key: String) {
        youTubePlayer?.cueVideo(key, 0f)
    }

    fun initMovieDetail() {
        viewModelScope.launch {
            movieDetail.value = null
            pageReview = 0
            maxPageReview = 1
            listReview.value.clear()
            listVideo.value.clear()
            youTubePlayer?.pause()
            youTubePlayer = null
            youTubePlayerView?.release()
            youTubePlayerView = null
        }
    }

    fun addMovieToFavorite(movie: MovieDetail, io: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(io) {
            favoriteMovieRepository.insert(movie)
            isMovieFavourite(movie.id)
        }
    }

    fun deleteMovieFromFavourite(movieId: Int, io: CoroutineDispatcher = Dispatchers.IO) {
        viewModelScope.launch(io) {
            favoriteMovieRepository.deleteByMovieId(movieId)
            isMovieFavourite(movieId)
        }
    }

    private val _movieFavorite = MutableStateFlow(false)
    val movieFavorite = _movieFavorite.asStateFlow()
    private fun isMovieFavourite(
        movieId: Int,
        io: CoroutineDispatcher = Dispatchers.IO,
        main: CoroutineDispatcher = Dispatchers.Main
    ) {
        viewModelScope.launch(io) {
            val a = favoriteMovieRepository.isMovieFavorite(movieId)
            _movieFavorite.emit(
                a
            )
        }
    }
}