package com.zeyadsadaka.bamtest.ui.screens.mainscreen

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeyadsadaka.bamtest.dataStore
import com.zeyadsadaka.bamtest.repositories.PokemonRepository
import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val application: Application,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState.Initial)
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    init {
        getPokemons()
    }

    fun getPokemons() {
        _uiState.value = MainScreenUiState.Loading
        viewModelScope.launch {
            try {
                val pokemonResult = pokemonRepository.getAllPokemons()
                if (pokemonResult.isSuccessful) {
                    application.dataStore.data.map { preferences ->
                        preferences[
                            booleanPreferencesKey("isDarkTheme")
                        ] ?: false
                    }.collect { isDarkTheme ->
                        pokemonResult.body()?.results?.let {
                            _uiState.value =
                                MainScreenUiState.Content(it, isDarkTheme)
                        } ?: run {
                            _uiState.value = MainScreenUiState.Error("")
                        }
                    }
                } else {
                    // TODO Add error message or error handler
                    _uiState.value = MainScreenUiState.Error("")
                }


            } catch (e: Exception) {
                _uiState.value = MainScreenUiState.Error(e.stackTraceToString())
            }
        }
    }

    fun filterPokemons(
        pokemon: Pokemon,
        filter: String?,
    ): Boolean =
        if (filter.isNullOrBlank() || filter == "All") {
            true
        } else {
            pokemon.name.startsWith(prefix = filter, ignoreCase = true)
        }

    fun saveIsDarkTheme(
        isDarkTheme: Boolean,
        context: Context
    ) {
        viewModelScope.launch {
            context.dataStore.edit { settings ->
                settings[booleanPreferencesKey("isDarkTheme")] = isDarkTheme
            }
        }
    }
}