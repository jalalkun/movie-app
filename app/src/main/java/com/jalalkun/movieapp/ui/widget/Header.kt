package com.jalalkun.movieapp.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jalalkun.movieapp.R

@Composable
fun Header(
    withBack: Boolean = false,
    backAction: (() -> Unit)? = null,
    title: String,
    secondButton: Any? = null,
    secondAction: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (withBack) IconButton(
            onClick = {
                backAction?.let {
                    it()
                }
            }) {
            AsyncImage(
                model = R.drawable.baseline_arrow_back_24,
                contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
        }
        Text(
            text = title, modifier = Modifier
                .weight(1f)
                .then(
                    if (!withBack) Modifier.padding(start = 24.dp)
                    else Modifier
                )
        )
        if (secondButton != null) IconButton(onClick = {
            secondAction?.let {
                it()
            }
        }) {
            AsyncImage(
                model = secondButton,
                contentDescription = "",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}