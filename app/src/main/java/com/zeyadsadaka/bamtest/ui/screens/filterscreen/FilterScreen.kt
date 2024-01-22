package com.zeyadsadaka.bamtest.ui.screens.filterscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zeyadsadaka.bamtest.R
import com.zeyadsadaka.bamtest.ui.components.ClickableListItem

@Composable
fun FilterScreen(
    navController: NavController,
    viewModel: FilterScreenViewModel = hiltViewModel(),
) {

    val filterItemsState by viewModel.filterItems.collectAsState()

    Surface {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val filterItems = filterItemsState
            Text(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                text = stringResource(
                    id = R.string.filter_screen_title
                ),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )

            LazyColumn {
                items(filterItems.size) { key ->
                    ClickableListItem(
                        text = filterItems[key],
                        lastItem = (key == filterItems.size - 1),
                        onItemClicked = { filter ->
                            navController.previousBackStackEntry
                                ?.savedStateHandle
                                ?.set("filter", filter)
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
