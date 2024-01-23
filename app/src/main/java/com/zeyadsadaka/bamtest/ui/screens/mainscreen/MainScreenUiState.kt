package com.zeyadsadaka.bamtest.ui.screens.mainscreen

import com.zeyadsadaka.bamtest.repositories.dto.Pokemon

sealed class MainScreenUiState {
    data object Initial : MainScreenUiState()
    data object Loading : MainScreenUiState()
    data class Content(val pokemons: List<Pokemon>, val isDarkMode: Boolean) : MainScreenUiState()
    data class Error(val errorMessage: String) : MainScreenUiState()
}