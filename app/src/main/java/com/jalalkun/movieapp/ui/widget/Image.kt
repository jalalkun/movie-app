package com.jalalkun.movieapp.ui.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.jalalkun.movieapp.BuildConfig

@Composable
fun ImageLoad(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String = ""
) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(BuildConfig.IMAGE_URL + path)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()
        ,
        contentDescription = contentDescription,
        modifier = modifier
    )
}