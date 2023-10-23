package com.jalalkun.movieapp.ui.home.movie_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jalalkun.movieapp.models.MovieResponse
import com.jalalkun.movieapp.state.ResultState
import com.jalalkun.movieapp.ui.MovieAppViewModel
import com.jalalkun.movieapp.ui.widget.Error
import com.jalalkun.movieapp.ui.widget.Loading
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import com.jalalkun.movieapp.helper.isScrolledToTheEnd
import com.jalalkun.movieapp.models.Genre
import com.jalalkun.movieapp.navigation.MovieNav.toDetailMovieScreen
import com.jalalkun.movieapp.ui.widget.Header

@Composable
fun ListMovieScreen(
    viewModel: MovieAppViewModel,
    genre: Genre,
    navHostController: NavHostController
) {
    when (
        val movieState = viewModel.listMovieState.collectAsState().value
    ) {
        is ResultState.Success<*> -> {
            if (movieState.data is MovieResponse) viewModel.setListMovie(movieState.data)
            viewModel.dismissMovieState()
        }

        is ResultState.Loading -> {
            Loading()
        }

        is ResultState.Error -> {
            Error(movieState.e) {
                viewModel.dismissMovieState()
                navHostController.popBackStack()
            }
        }

        is ResultState.Idle -> {
            // do nothing
        }
    }
    val lazyListState = rememberLazyListState()
    val listMovie = remember {
        viewModel.listMovie.value
    }
    Column {
        Header(withBack = true,
            backAction = {
                viewModel.initMovie()
                navHostController.popBackStack()
            },
            title = "Movies : ${genre.name}")
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            content = {
                items(
                    items = listMovie,
                    key = { movie ->
                        movie.id
                    },
                    itemContent = {
                        ListMovieItem(movie = it, onClick = { movie ->
                            navHostController.toDetailMovieScreen(movie.id.toString())
                        })
                    }
                )
            },
            state = lazyListState
        )
    }
    LaunchedEffect(key1 = Unit, block = {
        if (viewModel.listMovie.value.isEmpty()) viewModel.getListMovie(genre.id.toString())
        snapshotFlow { lazyListState.isScrolledToTheEnd() }
            .collect {
                viewModel.getListMovie(genre.id.toString())
            }
    })
    BackHandler {
        viewModel.initMovie()
        navHostController.popBackStack()
    }
}