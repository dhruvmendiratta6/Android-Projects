package com.example.booker_kotlin

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

private const val BASE_URL = "https://www.googleapis.com/"

private val moshiConverter = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshiConverter))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface BookApiService {
    @GET
    suspend fun getBooksResponse(@Url url: String): BaseModel
}

object BookApi{
    val retrofitService : BookApiService by lazy { retrofit.create(BookApiService::class.java) }
}