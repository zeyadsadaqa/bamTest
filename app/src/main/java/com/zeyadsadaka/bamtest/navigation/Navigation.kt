package com.zeyadsadaka.bamtest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.zeyadsadaka.bamtest.ui.screens.detailsscreen.PokemonDetailsScreen
import com.zeyadsadaka.bamtest.ui.screens.filterscreen.FilterScreen
import com.zeyadsadaka.bamtest.ui.screens.mainscreen.MainScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }

        composable(
            route = Screen.PokemonDetailsScreen.route + "/{pokemonName}",
            arguments = listOf(navArgument("pokemonName") {
                type = NavType.StringType
                defaultValue = ""
                nullable = false
            }),
        ) { entry ->
            PokemonDetailsScreen(
                pokemonName = entry.arguments?.getString("pokemonName") ?: "",
                navController = navController,
            )
        }

        composable(
            route = Screen.FilterScreen.route,
        ) {
            FilterScreen(
                navController = navController,
            )
        }
    }
}