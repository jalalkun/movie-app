package com.jalalkun.movieapp.ui.home

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.jalalkun.movieapp.R
import com.jalalkun.movieapp.helper.MoshiHelper
import com.jalalkun.movieapp.models.Genre
import com.jalalkun.movieapp.navigation.MovieNav.toFavoriteMovieScreen
import com.jalalkun.movieapp.navigation.MovieNav.toListMovieScreen
import com.jalalkun.movieapp.state.ResultState
import com.jalalkun.movieapp.ui.MovieAppViewModel
import com.jalalkun.movieapp.ui.widget.Error
import com.jalalkun.movieapp.ui.widget.Header
import com.jalalkun.movieapp.ui.widget.Loading

@Composable
fun HomeUIScreen(
    viewModel: MovieAppViewModel,
    navHostController: NavHostController
) {

    when (
        val state = viewModel.genreState.collectAsState().value
    ) {
        is ResultState.Success<*> -> {
            if (state.data is List<*>) viewModel.setListGenre(state.data)
            viewModel.dismissGenre()
        }

        is ResultState.Loading -> {
            Loading()
        }

        is ResultState.Error -> {
            Error(state.e) {
                viewModel.dismissGenre()
            }
        }

        is ResultState.Idle -> {
            // do nothing
        }
    }


    val lazyListState = rememberLazyListState()
    val listGenre = remember {
        viewModel.listGenre.value
    }
    Column {
        Header(title = "Genre", secondButton = R.drawable.baseline_favorite_24, secondAction = {
            navHostController.toFavoriteMovieScreen()
        })
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            content = {
                items(
                    items = listGenre,
                    key = { item ->
                        item.id
                    },
                    itemContent = {
                        ItemGenre(data = it, onClick = { genre ->
                            navHostController.toListMovieScreen(MoshiHelper.convertToJson(genre, Genre::class.java))
                        })
                    }
                )
            },
            state = lazyListState
        )
    }

    LaunchedEffect(key1 = Unit, block = {
        if (viewModel.listGenre.value.isEmpty()) viewModel.getGenre()
    })
}