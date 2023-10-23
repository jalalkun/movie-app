package com.jalalkun.movieapp.ui.favorite_movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jalalkun.movieapp.helper.isScrolledToTheEnd
import com.jalalkun.movieapp.models.movie_detail.MovieDetail.Companion.toMovie
import com.jalalkun.movieapp.navigation.MovieNav.toDetailMovieScreen
import com.jalalkun.movieapp.state.ResultState
import com.jalalkun.movieapp.ui.home.movie_screen.ListMovieItem
import com.jalalkun.movieapp.ui.widget.Error
import com.jalalkun.movieapp.ui.widget.Header
import com.jalalkun.movieapp.ui.widget.Loading
import com.pluto.plugins.logger.PlutoLog

@Composable
fun FavoriteMovieScreen(
    viewModel: FavoriteMovieViewModel,
    navHostController: NavHostController
){
    val movieState = viewModel.moviesState.collectAsState().value
    val lazyListState = rememberLazyListState()
    if (movieState is ResultState.Success<*>){
        PlutoLog.e("movieState", "success")
        if (movieState.data is List<*>){
            viewModel.setMovieList(movieState.data)
        }
    }
    if (movieState is ResultState.Loading) Loading()
    if (movieState is ResultState.Error) Error(movieState.e){

    }
    Column {
        Header(withBack = true,
            backAction = {
                navHostController.popBackStack()
            },
            title = "Movies Favorite")
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            content = {
                items(
                    items = viewModel.movieList.value,
                    key = { movie ->
                        movie.id
                    },
                    itemContent = {
                        ListMovieItem(movie = it.toMovie(), onClick = { movie ->
                            navHostController.toDetailMovieScreen(movie.id.toString())
                        })
                    }
                )
            },
            state = lazyListState
            )
    }
    LaunchedEffect(key1 = Unit, block = {
        if (viewModel.movieList.value.isEmpty()) viewModel.onEvent(FavoriteMovieEvent.GetFavoriteMovie)
        snapshotFlow { lazyListState.isScrolledToTheEnd() }
            .collect {
                viewModel.onEvent(FavoriteMovieEvent.GetFavoriteMovie)
            }
    })
}