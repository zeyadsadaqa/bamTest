package com.zeyadsadaka.bamtest.ui.screens.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.zeyadsadaka.bamtest.ui.components.ErrorScreen
import com.zeyadsadaka.bamtest.ui.components.InitialStateScreen
import com.zeyadsadaka.bamtest.ui.components.LoadingStateScreen
import com.zeyadsadaka.bamtest.R
import com.zeyadsadaka.bamtest.navigation.Screen
import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import com.zeyadsadaka.bamtest.ui.components.ClickableListItem

@Composable
fun MainScreen(
    navController: NavController?,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()

    val filter = navController?.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow(key = "filter", initialValue = "All")

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
            // Show the list of pokemons
            if (filter != null) {
                ContentStateScreen(
                    pokemons = (uiState as MainScreenUiState.Content).pokemons.filter {
                        viewModel.filterPokemons(it, filter.value)
                    },
                    filter = filter.collectAsState().value,
                    onPokemonClicked = { pokemonName ->
                        navController.navigate(
                            Screen.PokemonDetailsScreen.withArgs(pokemonName)
                        )
                    },
                    onFilterClicked = {
                        navController.navigate(
                            Screen.FilterScreen.route,
                            NavOptions.Builder()
                                .setLaunchSingleTop(true)
                                .build()
                        )
                    },
                    onSwitchChangeListener = {
                        viewModel.saveIsDarkTheme(it)
                    },
                    isDarkMode = (uiState as MainScreenUiState.Content).isDarkMode,
                )
            }
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
    isDarkMode: Boolean,
    filter: String,
    onPokemonClicked: (pokemonName: String) -> Unit,
    onFilterClicked: () -> Unit,
    onSwitchChangeListener: (isDarkMode: Boolean) -> Unit,
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
            ConfigurationHeader(
                onFilterClicked = onFilterClicked,
                onSwitchChangeListener = onSwitchChangeListener,
                isDarkMode = isDarkMode,
                filter = filter,
            )
            if (pokemons.isNotEmpty()) {
                LazyColumn {
                    items(pokemons.size) { key ->
                        ClickableListItem(
                            text = pokemons[key].name,
                            lastItem = (key == pokemons.size - 1),
                            onItemClicked = { pokemonName ->
                                onPokemonClicked(pokemonName)
                            }
                        )
                    }
                }
            } else {
                Text(
                    text = stringResource(id = R.string.no_pokemons_error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 12.dp,
                        ),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
@Preview
fun ContentStateScreenPreview() {
    val list = listOf(
        Pokemon("bulbasaur"),
        Pokemon("charizard"),
        Pokemon("blastoise"),
    )
    ContentStateScreen(
        pokemons = list,
        onPokemonClicked = {},
        onFilterClicked = {},
        onSwitchChangeListener = {},
        isDarkMode = false,
        filter = "All"
    )
}

@Composable
fun ConfigurationHeader(
    isDarkMode: Boolean,
    filter: String,
    onFilterClicked: () -> Unit,
    onSwitchChangeListener: (isDarkMode: Boolean) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 4.dp, end = 4.dp, top = 8.dp, bottom = 8.dp
            )
            .background(
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(4.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = stringResource(id = R.string.dark_mode_lbl),
        )

        var checked by remember { mutableStateOf(isDarkMode) }
        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
                onSwitchChangeListener(it)
            }
        )
        Spacer(modifier = Modifier.weight(0.75f))

        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Gray,
                )
            ) {
                append(stringResource(id = R.string.filter_title))
            }

            pushStringAnnotation(
                tag = "all",
                annotation = "all"
            )

            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    textDecoration = TextDecoration.Underline
                )
            ) {
                append(filter)
            }
            pop()
        }

        Text(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .clickable {
                    onFilterClicked()
                },
            text = annotatedText
        )
    }
}

@Preview
@Composable
fun ConfigurationHeaderPreview() {
    ConfigurationHeader(
        isDarkMode = false,
        filter = "All",
        onFilterClicked = {},
        onSwitchChangeListener = {},
    )
}


