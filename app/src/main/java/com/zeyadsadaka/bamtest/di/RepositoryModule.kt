package com.zeyadsadaka.bamtest.di

import com.zeyadsadaka.bamtest.database.PokemonDB
import com.zeyadsadaka.bamtest.database.PokemonEntityAdapter
import com.zeyadsadaka.bamtest.network.AppAPI
import com.zeyadsadaka.bamtest.repositories.PokemonRepository
import com.zeyadsadaka.bamtest.repositories.PokemonRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepositoryModule {
    @Provides
    fun providePokemonRepository(
        api: AppAPI,
        db: PokemonDB,
        adapter: PokemonEntityAdapter,
    ): PokemonRepository {
        return PokemonRepositoryImpl(api, db, adapter)
    }

    @Provides
    fun providePokemonEntityAdapter() = PokemonEntityAdapter()
}