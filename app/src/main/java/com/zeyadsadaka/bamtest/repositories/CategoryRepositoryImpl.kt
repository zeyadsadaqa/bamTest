package com.zeyadsadaka.bamtest.repositories

import com.zeyadsadaka.bamtest.network.AppAPI
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiClient: AppAPI
) : CategoryRepository {
    override suspend fun getAllRepositories() = apiClient.getCategories()
}