package com.zeyadsadaka.bamtest.ui.screens.detailsscreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun PokemonDetailsScreen(
    pokemonName: String,
    navController: NavController,
) {
    Text(text = pokemonName)

}