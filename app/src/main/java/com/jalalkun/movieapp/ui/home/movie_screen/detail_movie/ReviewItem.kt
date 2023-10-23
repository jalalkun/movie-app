package com.jalalkun.movieapp.ui.home.movie_screen.detail_movie

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jalalkun.movieapp.models.Review
import com.jalalkun.movieapp.ui.widget.ImageLoad

@Composable
fun ReviewItem(review: Review) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            ImageLoad(
                path = review.author_details.avatar_path ?: "",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Column {
                Text(text = review.author, style = MaterialTheme.typography.titleLarge)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = review.content)
            }
        }
    }
}