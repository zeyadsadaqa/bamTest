package com.zeyadsadaka.bamtest.ui.screens.detailsscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun APIDetailsScreen(
//    viewModel: ShoppingListDetailsViewModel = hiltViewModel(),
    categoryName: String,
    navController: NavController,
) {
    Text(text = categoryName)

}