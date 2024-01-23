package com.zeyadsadaka.bamtest.ui.screens.detailsscreen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.zeyadsadaka.bamtest.repositories.dto.Ability
import com.zeyadsadaka.bamtest.repositories.dto.AbilityContainer
import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.Sprites
import com.zeyadsadaka.bamtest.ui.components.ErrorScreen
import com.zeyadsadaka.bamtest.ui.components.InitialStateScreen
import com.zeyadsadaka.bamtest.ui.components.LoadingStateScreen
import com.zeyadsadaka.bamtest.R

@Composable
fun PokemonDetailsScreen(
    viewModel: DetailsScreenViewModel = hiltViewModel(),
    pokemonName: String,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPokemonDetails(pokemonName)
    }
    when (uiState) {
        DetailsScreenUiState.Initial -> {
            // Show Initial screen
            InitialStateScreen()
        }

        DetailsScreenUiState.Loading -> {
            // Show loading screen
            LoadingStateScreen()
        }

        is DetailsScreenUiState.Content -> {
            // Show the list of categories
            ContentScreen(
                pokemon = (uiState as DetailsScreenUiState.Content).pokemon,
            )
        }

        is DetailsScreenUiState.Error -> {
            // Show the error message
            ErrorScreen(
                onButtonClicked = {
                    viewModel.getPokemonDetails(pokemonName)
                }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ContentScreen(pokemon: PokemonDetails) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = pokemon.name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            GlideImage(
                modifier = Modifier
                    .width(256.dp)
                    .height(256.dp),
                model = pokemon.sprites.frontDefault,
                contentDescription = null,
                failure = placeholder(R.drawable.poke_ball),
                loading = placeholder(R.drawable.poke_ball_grey),

                )

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.abilities_title),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.titleLarge
            )
            pokemon.abilities.forEach { abilityContainer ->
                Text(
                    modifier = Modifier
                        .padding(start = 32.dp, bottom = 2.dp)
                        .fillMaxWidth(),
                    text = abilityContainer.ability.name,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun ContentScreenPreview() {
    ContentScreen(
        pokemon = PokemonDetails(
            abilities = listOf(
                AbilityContainer(
                    Ability(name = "abi")
                )
            ),
            name = "bulbasaur",
            sprites = Sprites(frontDefault = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
        )
    )
}

