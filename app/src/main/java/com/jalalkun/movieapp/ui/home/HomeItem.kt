package com.jalalkun.movieapp.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jalalkun.movieapp.models.Genre

@Composable
fun ItemGenre(data: Genre, onClick: (data: Genre) -> Unit){
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClick(data)
            }
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)){
            Text(text = data.name, modifier = Modifier.align(Alignment.CenterStart))
        }
    }
}