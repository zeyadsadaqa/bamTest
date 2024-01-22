package com.zeyadsadaka.bamtest.ui.states

sealed class UiState {
    data object Initial : UiState()
    data object Loading : UiState()
    data class Content(val categories: List<String>) : UiState() // convert it to T
    data class Error(val errorMessage: String) : UiState()
}