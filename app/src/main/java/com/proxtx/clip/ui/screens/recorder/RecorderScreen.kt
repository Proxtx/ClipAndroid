package com.proxtx.clip.ui.screens.recorder

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.WorkManager
import com.proxtx.clip.data.ClipRepository
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proxtx.clip.services.AudioRecorderService
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun RecorderScreen (modifier: Modifier = Modifier, recorderViewModel: RecorderViewModel = viewModel(factory = RecorderViewModel.Factory)) {
    val context = LocalContext.current;
    Button(modifier = modifier, onClick = {
        recorderViewModel.startRecorder(context)
    }) {
        Text("Click Me")
    }
}