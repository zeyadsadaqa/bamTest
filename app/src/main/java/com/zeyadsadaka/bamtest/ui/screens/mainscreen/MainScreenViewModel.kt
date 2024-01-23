package com.zeyadsadaka.bamtest.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeyadsadaka.bamtest.database.KeyValueConstants
import com.zeyadsadaka.bamtest.database.KeyValueStore
import com.zeyadsadaka.bamtest.repositories.PokemonRepository
import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
    private val keyValueStore: KeyValueStore,
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

                keyValueStore
                    .getBooleanFlow(KeyValueConstants.IS_DARK_THEME_KEY)
                    .collect { isDarkTheme ->
                        pokemonResult.results.let {
                            _uiState.value =
                                MainScreenUiState.Content(it, isDarkTheme)
                        }
                    }
            } catch (e: Exception) {
                _uiState.value = MainScreenUiState.Error
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
    ) {
        viewModelScope.launch {
            keyValueStore.saveBoolean(
                key = KeyValueConstants.IS_DARK_THEME_KEY,
                value = isDarkTheme,
            )
        }
    }
}