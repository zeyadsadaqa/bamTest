package com.zeyadsadaka.bamtest.ui.screens.detailsscreen

import com.zeyadsadaka.bamtest.repositories.dto.Pokemon


sealed class DetailsScreenUiState {
    data object Initial : DetailsScreenUiState()
    data object Loading : DetailsScreenUiState()
    data class Content(val pokemon: Pokemon) : DetailsScreenUiState()
    data class Error(val errorMessage: String) : DetailsScreenUiState()
}