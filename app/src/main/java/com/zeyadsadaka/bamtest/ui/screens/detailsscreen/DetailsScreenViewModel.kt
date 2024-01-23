package com.zeyadsadaka.bamtest.ui.screens.detailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeyadsadaka.bamtest.repositories.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<DetailsScreenUiState> =
        MutableStateFlow(DetailsScreenUiState.Initial)
    val uiState: StateFlow<DetailsScreenUiState> = _uiState.asStateFlow()

    fun getPokemonDetails(pokemonName: String) {
        _uiState.value = DetailsScreenUiState.Loading

        viewModelScope.launch {
            try {
                val pokemonResult =
                    pokemonRepository.getPokemonDetails(pokemonName)

                _uiState.value = DetailsScreenUiState.Content(pokemonResult)
            } catch (e: Exception) {
                _uiState.value = DetailsScreenUiState.Error(e.stackTraceToString())
            }
        }
    }
}