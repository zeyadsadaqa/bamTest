package com.zeyadsadaka.bamtest.repositories

import com.zeyadsadaka.bamtest.repositories.dto.CategoryResponse
import retrofit2.Response

interface CategoryRepository {
    suspend fun getAllRepositories(): Response<CategoryResponse>
}
