package com.zeyadsadaka.bamtest.di

import com.zeyadsadaka.bamtest.network.AppAPI
import com.zeyadsadaka.bamtest.repositories.CategoryRepository
import com.zeyadsadaka.bamtest.repositories.CategoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun provideBlogRepository(api: AppAPI): CategoryRepository {
        return CategoryRepositoryImpl(api)
    }
}