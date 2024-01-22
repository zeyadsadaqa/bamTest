package com.zeyadsadaka.bamtest.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ClickableListItem(
    text: String,
    lastItem: Boolean,
    onItemClicked: (value: String) -> Unit,
) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClicked(text)
            }
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 12.dp,
                bottom = 12.dp,
            ),
        style = MaterialTheme.typography.bodyLarge

    )

    if (!lastItem) {
        Divider(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .height(1.dp)
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
fun ClickableListItemPreview() {
    ClickableListItem(
        text = "A",
        lastItem = false,
        onItemClicked = {},
    )
}
