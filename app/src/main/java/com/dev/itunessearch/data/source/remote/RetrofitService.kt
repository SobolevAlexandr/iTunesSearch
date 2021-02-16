package com.dev.itunessearch.data.source.remote

import com.dev.itunessearch.domain.model.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("search")
    suspend fun search(
        @Query("media") mediaFilter: String,
        @Query("term") searchQuery: String
    ): Response

    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
    }
}