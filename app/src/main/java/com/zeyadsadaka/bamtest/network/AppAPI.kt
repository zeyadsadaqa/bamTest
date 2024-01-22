package com.zeyadsadaka.bamtest.network

import com.zeyadsadaka.bamtest.repositories.dto.CategoryResponse
import retrofit2.Response
import retrofit2.http.GET

interface AppAPI {
    @GET("categories")
    suspend fun getCategories(): Response<CategoryResponse>
}