package com.zeyadsadaka.bamtest.ui.screens.filterscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FilterScreenViewModel : ViewModel() {

    private val _filterItems = MutableStateFlow(listOf(""))
    val filterItems: StateFlow<List<String>> = _filterItems

    init {
        getFilterItems()
    }

    private fun getFilterItems() {
        viewModelScope.launch {
            generateAlphabets()
        }
    }

    private fun generateAlphabets() {
        val alphabets = mutableListOf("All")
        for (i in 'A'..'Z') {
            alphabets.add(i.toString())
        }
        _filterItems.value = alphabets
    }
}