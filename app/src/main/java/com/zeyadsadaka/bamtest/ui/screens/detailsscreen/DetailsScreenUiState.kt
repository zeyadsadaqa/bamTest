package com.zeyadsadaka.bamtest.ui.screens.detailsscreen

import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails


sealed class DetailsScreenUiState {
    data object Initial : DetailsScreenUiState()
    data object Loading : DetailsScreenUiState()
    data class Content(val pokemon: PokemonDetails) : DetailsScreenUiState()
    data class Error(val errorMessage: String) : DetailsScreenUiState()
}