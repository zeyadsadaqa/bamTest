package com.zeyadsadaka.bamtest.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zeyadsadaka.bamtest.R

typealias OnButtonClicked = () -> Unit
@Composable
fun ErrorScreen(onButtonClicked: OnButtonClicked) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.error_title),
                    style = MaterialTheme.typography.titleLarge
                )
                Button(
                    onClick = {
                        onButtonClicked()
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = stringResource(id = R.string.retry_btn_lbl), fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
@Preview
fun ErrorScreenPreview() {
    ErrorScreen(onButtonClicked = {})
}