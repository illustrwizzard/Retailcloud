package com.example.retailcloudproject.Retrofit

import com.example.retailcloudproject.Room.ImageItem
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("list")
    suspend fun getImages(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<ImageItem>
}