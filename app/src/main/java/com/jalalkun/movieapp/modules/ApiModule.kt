package com.jalalkun.movieapp.modules

import com.jalalkun.movieapp.network.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single(createdAtStart = false) { get<Retrofit>().create(ApiService::class.java) }
}