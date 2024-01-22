package com.zeyadsadaka.bamtest.ui.screens.mainscreen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zeyadsadaka.bamtest.ui.components.ErrorScreen
import com.zeyadsadaka.bamtest.ui.components.InitialStateScreen
import com.zeyadsadaka.bamtest.ui.components.LoadingStateScreen
import com.zeyadsadaka.bamtest.R
import com.zeyadsadaka.bamtest.navigation.Screen
import com.zeyadsadaka.bamtest.repositories.dto.Pokemon

@Composable
fun MainScreen(
    navController: NavController?,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        MainScreenUiState.Initial -> {
            // Show Initial screen
            InitialStateScreen()
        }

        MainScreenUiState.Loading -> {
            // Show loading screen
            LoadingStateScreen()
        }

        is MainScreenUiState.Content -> {
            // Show the list of categories
            ContentStateScreen(
                pokemons = (uiState as MainScreenUiState.Content).pokemons,
                onPokemonClicked = { pokemonName ->
                    navController?.navigate(
                        Screen.PokemonDetailsScreen.withArgs(pokemonName)
                    )
                }
            )
        }

        is MainScreenUiState.Error -> {
            // Show the error message
            ErrorScreen(
                onButtonClicked = { viewModel.getPokemons() }
            )
        }
    }


}


@Composable
fun ContentStateScreen(
    pokemons: List<Pokemon>,
    onPokemonClicked: (pokemonName: String) -> Unit
) {
    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = stringResource(
                    id = R.string.pokemons_screen_title
                ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            LazyColumn {
                items(pokemons.size) { key ->
                    Text(
                        text = pokemons[key].name,
                        modifier = Modifier
                            .padding(
                                start = 12.dp,
                                end = 12.dp,
                                top = 12.dp,
                                bottom = 12.dp,
                            )
                            .clickable {
                                onPokemonClicked(pokemons[key].name)
                            },
                        style = MaterialTheme.typography.bodyLarge

                    )

                    if (key < pokemons.lastIndex) {
                        Divider(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(1.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun ContentStateScreenPreview() {
    val list = listOf(
        Pokemon("1"),
    )
    ContentStateScreen(
        pokemons = list,
        onPokemonClicked = {},
    )
}



