package com.jalalkun.movieapp.ui.home.movie_screen.detail_movie

import android.view.View
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.jalalkun.movieapp.models.movie_detail.MovieDetail
import com.jalalkun.movieapp.state.ResultState
import com.jalalkun.movieapp.ui.MovieAppViewModel
import com.jalalkun.movieapp.ui.widget.Error
import com.jalalkun.movieapp.ui.widget.ImageLoad
import com.jalalkun.movieapp.ui.widget.Loading
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.runtime.snapshotFlow
import coil.compose.AsyncImage
import com.jalalkun.movieapp.R
import com.jalalkun.movieapp.helper.isScrolledToTheEnd
import com.jalalkun.movieapp.models.ReviewResponse
import com.jalalkun.movieapp.models.movie_detail.MovieDetail.Companion.toMovie
import com.jalalkun.movieapp.ui.widget.Header
import com.pluto.plugins.logger.PlutoLog

@Composable
fun DetailMovieScreen(
    id: Int,
    viewModel: MovieAppViewModel,
    navHostController: NavHostController
) {

    val state = viewModel.detailMovieState.collectAsState().value
    if (state is ResultState.Success<*>) {
        if (state.data is MovieDetail) viewModel.setMovieDetail(state.data)
        viewModel.dismissMovieDetail()
    }

    if (state is ResultState.Loading) {
        Loading()
    }

    if (state is ResultState.Error) {
        Error(state.e) {
            viewModel.dismissMovieDetail()
            navHostController.popBackStack()
        }
    }

    if (viewModel.movieDetail.value != null) DetailMovieUI(
        movieDetail = viewModel.movieDetail.value!!,
        viewModel = viewModel,
        onBack = {
            viewModel.initMovieDetail()
            navHostController.popBackStack()
        }
    )
    LaunchedEffect(key1 = Unit, block = {
        viewModel.getDetailMovie(id)
    })
    BackHandler {
        viewModel.initMovieDetail()
        navHostController.popBackStack()
    }
}

@Composable
private fun DetailMovieUI(
    movieDetail: MovieDetail,
    viewModel: MovieAppViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val listVideo = remember {
        viewModel.listVideo.value
    }
    val listReview = remember {
        viewModel.listReview.value
    }
    val lazyListState = rememberLazyListState()
    val isFavourite = viewModel.movieFavorite.collectAsState().value
    PlutoLog.e("DetailMovieUI", "isFavorite $isFavourite")
    Column {
        Header(title = "Movie: ${movieDetail.original_title}",
            withBack = true,
            backAction = {
                onBack()
            },
            secondButton =
            if (isFavourite) R.drawable.baseline_favorite_24
            else R.drawable.baseline_favorite_border_24,
            secondAction = {
                if (isFavourite) {
                    viewModel.deleteMovieFromFavourite(movieDetail.id)
                } else {
                    viewModel.addMovieToFavorite(movieDetail)
                }
            }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            content = {
                item {
                    ElevatedCard {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ImageLoad(
                                path = movieDetail.backdrop_path, modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = movieDetail.title,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Spacer(modifier = Modifier.size(4.dp))
                            if (listVideo.isNotEmpty()) {
                                AndroidView(factory = {
                                    viewModel.getYoutubePlayerView(context) ?: View(context)
                                })
                            }
                            Spacer(modifier = Modifier.size(4.dp))
                            Text(
                                text = movieDetail.overview,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
                items(
                    items = listReview,
                    key = {
                        it.id
                    },
                    itemContent = {
                        ReviewItem(review = it)
                    }
                )
            },
            state = lazyListState
        )
    }
    when (
        val state = viewModel.listReviewState.collectAsState().value
    ) {
        is ResultState.Success<*> -> {
            if (state.data is ReviewResponse) viewModel.setReview(state.data)
            viewModel.dismissReview()
        }

        is ResultState.Loading -> {
//            Loading()
        }

        is ResultState.Error -> {
            Error(state.e) {
                viewModel.dismissReview()
            }
        }

        is ResultState.Idle -> {
            // do nothing
        }
    }
    LaunchedEffect(key1 = Unit, block = {
        if (viewModel.listVideo.value.isEmpty()) viewModel.getVideos(movieDetail.id)
        if (viewModel.listReview.value.isEmpty()) viewModel.getMovieReview(movieDetail.id)
        snapshotFlow {
            lazyListState.isScrolledToTheEnd()
        }.collect {
            viewModel.getMovieReview(id = movieDetail.id)
        }
    })

}