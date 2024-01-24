package com.zeyadsadaka.bamtest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.zeyadsadaka.bamtest.database.keyvalue.KeyValueConstants
import com.zeyadsadaka.bamtest.database.keyvalue.KeyValueStore
import com.zeyadsadaka.bamtest.navigation.Navigation
import com.zeyadsadaka.bamtest.ui.theme.BamTestTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var keyValueStore: KeyValueStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val isDarkTheme: Flow<Boolean?> = keyValueStore
            .getBooleanFlow(
                KeyValueConstants.IS_DARK_THEME_KEY
            )

        setContent {
            val darkTheme by isDarkTheme.collectAsState(initial = null)
            BamTestTheme(
                darkTheme = darkTheme ?: isSystemInDarkTheme()
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

