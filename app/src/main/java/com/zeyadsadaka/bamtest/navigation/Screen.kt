package com.zeyadsadaka.bamtest.navigation

sealed class Screen(
    val route: String
) {
    data object MainScreen : Screen("main_screen")

    data object PokemonDetailsScreen : Screen("details_screen")

    fun withArgs(vararg args: String) = buildString {
        append(route)
        args.forEach { arg ->
            append("/$arg")
        }
    }
}