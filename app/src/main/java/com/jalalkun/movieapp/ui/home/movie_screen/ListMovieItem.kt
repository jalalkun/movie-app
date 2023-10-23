package com.jalalkun.movieapp.ui.home.movie_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jalalkun.movieapp.models.Movie
import com.jalalkun.movieapp.ui.widget.ImageLoad
import com.jalalkun.movieapp.ui.widget.RatingBar

@Composable
fun ListMovieItem(
    movie: Movie,
    onClick: (data: Movie) -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClick(movie)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            ImageLoad(
                path = movie.poster_path, modifier = Modifier
                    .width(90.dp)
            )
            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.titleMedium)
                Text(text = movie.overview, style = MaterialTheme.typography.bodySmall)
                Text(text = "Popularity ${movie.popularity}")
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingBar(stars = 10, rating = movie.vote_average)
                    Text(text = movie.vote_average.toString())
                }
            }
        }
    }
}