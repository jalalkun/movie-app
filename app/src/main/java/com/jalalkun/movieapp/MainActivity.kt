package com.jalalkun.movieapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jalalkun.movieapp.helper.MoshiHelper
import com.jalalkun.movieapp.models.Genre
import com.jalalkun.movieapp.navigation.MovieNav.detailMoviePath
import com.jalalkun.movieapp.navigation.MovieNav.favoriteMoviePath
import com.jalalkun.movieapp.navigation.MovieNav.getParamGenre
import com.jalalkun.movieapp.navigation.MovieNav.getParamId
import com.jalalkun.movieapp.ui.MovieAppViewModel
import com.jalalkun.movieapp.ui.home.HomeUIScreen
import com.jalalkun.movieapp.navigation.MovieNav.homePath
import com.jalalkun.movieapp.navigation.MovieNav.listMoviePath
import com.jalalkun.movieapp.ui.favorite_movie.FavoriteMovieScreen
import com.jalalkun.movieapp.ui.favorite_movie.FavoriteMovieViewModel
import com.jalalkun.movieapp.ui.home.movie_screen.ListMovieScreen
import com.jalalkun.movieapp.ui.home.movie_screen.detail_movie.DetailMovieScreen
import com.jalalkun.movieapp.ui.theme.MovieAppTheme
import com.pluto.plugins.logger.PlutoLog
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetNavHost()
                }
            }
        }
    }

    @Composable
    private fun SetNavHost(
        movieAppViewModel: MovieAppViewModel = koinViewModel(),
        favoriteMovieViewModel: FavoriteMovieViewModel = koinViewModel()
    ) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = homePath()) {
            composable(homePath()) {
                HomeUIScreen(viewModel = movieAppViewModel, navHostController = navController)
            }
            composable(listMoviePath()) {
                val genre = it.arguments?.getString(getParamGenre())
                PlutoLog.e("SetNavHost", "genre: $genre")

                if (genre != null) {
                    val g = MoshiHelper.convertToObject(genre, Genre::class.java)
                    if (g == null) navController.popBackStack()
                    else ListMovieScreen(
                        viewModel = movieAppViewModel,
                        genre = g,
                        navHostController = navController
                    )
                }else{
                    navController.popBackStack()
                }
            }
            composable(detailMoviePath()) {
                val id = it.arguments?.getString(getParamId())
                if (id != null) {
                    DetailMovieScreen(
                        id = id.toInt(),
                        viewModel = movieAppViewModel,
                        navHostController = navController
                    )
                }
            }
            composable(favoriteMoviePath()) {
                favoriteMovieViewModel.init()
                FavoriteMovieScreen(
                    viewModel = favoriteMovieViewModel,
                    navHostController = navController
                )
            }
        }
    }
}

