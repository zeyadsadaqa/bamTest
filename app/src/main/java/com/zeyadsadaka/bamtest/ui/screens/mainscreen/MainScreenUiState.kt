package com.zeyadsadaka.bamtest.ui.screens.mainscreen

sealed class MainScreenUiState {
    data object Initial : MainScreenUiState()
    data object Loading : MainScreenUiState()
    data class Content(val categories: List<String>) : MainScreenUiState() // convert it to T
    data class Error(val errorMessage: String) : MainScreenUiState()
}