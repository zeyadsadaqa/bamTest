package com.zeyadsadaka.bamtest.ui.screens.detailsscreen

import androidx.lifecycle.ViewModel
import com.zeyadsadaka.bamtest.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository,
) : ViewModel() {


}