package com.jalalkun.movieapp.state

sealed class ResultState {
    data object Loading : ResultState()
    class Success<T>(val data: T) : ResultState()
    class Error(val e: Exception) : ResultState()
    data object Idle : ResultState()
}