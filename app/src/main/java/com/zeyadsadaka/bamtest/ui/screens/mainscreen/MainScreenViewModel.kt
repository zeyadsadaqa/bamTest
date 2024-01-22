package com.zeyadsadaka.bamtest.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                if (pokemonResult.isSuccessful && pokemonResult.body() != null) {
                    _uiState.value =
                        MainScreenUiState.Content(pokemonResult.body()?.results!!)
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

}