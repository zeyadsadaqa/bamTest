package com.zeyadsadaka.bamtest.ui.screens.detailsscreen


sealed class DetailsScreenUiState {
    data object Initial : DetailsScreenUiState()
    data object Loading : DetailsScreenUiState()
    data class Content(val categories: List<String>) : DetailsScreenUiState()
    data class Error(val errorMessage: String) : DetailsScreenUiState()
}