package com.zeyadsadaka.bamtest.database

import kotlinx.coroutines.flow.Flow

interface KeyValueStore {
    fun getBooleanFlow(key: String): Flow<Boolean>

    suspend fun saveBoolean(key: String, value: Boolean)
}