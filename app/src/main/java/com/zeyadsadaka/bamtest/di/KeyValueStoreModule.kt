package com.zeyadsadaka.bamtest.di

import android.app.Application
import com.zeyadsadaka.bamtest.database.KeyValueStore
import com.zeyadsadaka.bamtest.database.KeyValueStoreImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class KeyValueStoreModule {
    @Provides
    fun provideKeyStore(
        application: Application,
    ): KeyValueStore {
        return KeyValueStoreImpl(application)
    }
}