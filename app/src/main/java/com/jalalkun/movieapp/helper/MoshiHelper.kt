package com.jalalkun.movieapp.helper

import com.pluto.plugins.logger.PlutoLog
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class MoshiHelper {
    companion object {
        private val moshi: Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        fun <T> convertToJson(data: T, type: Class<T>): String {
            val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
            return jsonAdapter.toJson(data)
        }

        fun <T> convertToObject(data: String, type: Class<T>): T? {
            if (data.isEmpty()) return null
            PlutoLog.e("convert to object", data)
            val jsonAdapter: JsonAdapter<T> = moshi.adapter(type)
            PlutoLog.e("object", "a")
            return jsonAdapter.fromJson(data)
        }
    }
}