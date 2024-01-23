package com.zeyadsadaka.bamtest.database

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class KeyValueStoreImpl @Inject constructor(
    private val application: Application,
) : KeyValueStore {
    override fun getBooleanFlow(key: String) =
        application.dataStore.data.map { preferences ->
            preferences[
                booleanPreferencesKey(key)
            ] ?: false
        }

    override suspend fun saveBoolean(key: String, value: Boolean) {
        application.dataStore.edit { settings ->
            settings[booleanPreferencesKey(key)] = value
        }
    }
}