package com.zeyadsadaka.bamtest.ui.screens.mainscreen


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
import com.zeyadsadaka.bamtest.ui.components.ErrorScreen
import com.zeyadsadaka.bamtest.ui.components.InitialStateScreen
import com.zeyadsadaka.bamtest.ui.components.LoadingStateScreen
import com.zeyadsadaka.bamtest.ui.states.UiState
import com.zeyadsadaka.bamtest.R

@Composable
fun MainScreen(viewModel: MainScreenViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        UiState.Initial -> {
            // Show Initial screen
            InitialStateScreen()
        }

        UiState.Loading -> {
            // Show loading screen
            LoadingStateScreen()
        }

        is UiState.Content -> {
            // Show the list of categories
            ContentStateScreen((uiState as UiState.Content).categories)
        }

        is UiState.Error -> {
            // Show the error message
            ErrorScreen(
                onButtonClicked = { viewModel.getCategories() }
            )
        }
    }


}

@Composable
fun ContentStateScreen(categories: List<String>) {
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
                    id = R.string.category_screen_title
                ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            LazyColumn {
                items(categories.size) { key ->
                    Text(
                        text = categories[key],
                        modifier = Modifier.padding(
                            start = 12.dp,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 12.dp,
                        ),
                        style = MaterialTheme.typography.bodyLarge

                    )

                    if (key < categories.lastIndex) {
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
        "Animals",
        "Anime",
        "Art and Design",
        "Patent",
        "Transportation",
        "Weather",
    )
    ContentStateScreen(list)
}



