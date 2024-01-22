package com.zeyadsadaka.bamtest.ui.screens.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zeyadsadaka.bamtest.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<MainScreenUiState> =
        MutableStateFlow(MainScreenUiState.Initial)
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    init {
        getCategories()
    }

    fun getCategories() {
        _uiState.value = MainScreenUiState.Loading
        viewModelScope.launch {
            try {
                val categoriesResult = categoryRepository.getAllRepositories()
                if (categoriesResult.isSuccessful && categoriesResult.body() != null) {
                    _uiState.value =
                        MainScreenUiState.Content(categoriesResult.body()?.categories!!)
                } else {
                    // TODO Add error message or error handler
                    _uiState.value = MainScreenUiState.Error("")
                }


            } catch (e: Exception) {
                _uiState.value = MainScreenUiState.Error(e.stackTraceToString())
            }
        }
    }
}