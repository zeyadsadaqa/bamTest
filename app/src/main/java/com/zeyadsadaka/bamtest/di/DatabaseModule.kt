package com.zeyadsadaka.bamtest.di

import android.content.Context
import com.zeyadsadaka.bamtest.database.PokemonDB
import com.zeyadsadaka.bamtest.database.PokemonDatabase
import com.zeyadsadaka.bamtest.database.PokemonDatabaseImpl
import com.zeyadsadaka.bamtest.database.PokemonEntityAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabaseInstance(@ApplicationContext context: Context): PokemonDB {
        return PokemonDB.getInstance(context)
    }

    @Provides
    @Singleton
    fun providePokemonDatabase(
        pokemonDB: PokemonDB,
        pokemonEntityAdapter: PokemonEntityAdapter,
    ): PokemonDatabase {
       return PokemonDatabaseImpl(pokemonDB, pokemonEntityAdapter)
    }

    @Provides
    @Singleton
    fun providePokemonEntityAdapter(
    ): PokemonEntityAdapter = PokemonEntityAdapter()

}
