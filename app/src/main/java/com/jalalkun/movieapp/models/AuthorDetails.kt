package com.jalalkun.movieapp.models

data class AuthorDetails(
    val avatar_path: String ? = null,
    val name: String,
    val rating: Int,
    val username: String
)