package com.proxtx.clip

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.proxtx.clip.ui.screens.recorder.RecorderScreen
import com.proxtx.clip.ui.screens.recorder.RecorderViewModel

enum class ClipScreen(@StringRes val title: Int, showTitle: Boolean = true) {
    Intro(title = R.string.app_name, showTitle = false),
    Recorder(title = R.string.app_name)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClipAppBar(
    currentScreen: ClipScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun ClipApp(
    //viewModel:
) {
    Scaffold(topBar = {
        ClipAppBar(
            currentScreen = ClipScreen.Intro,
            canNavigateBack = true,
            navigateUp = {}
        )
    }) { contentPadding ->
        Log.i("HI", contentPadding.toString())
        RecorderScreen()
    }
}