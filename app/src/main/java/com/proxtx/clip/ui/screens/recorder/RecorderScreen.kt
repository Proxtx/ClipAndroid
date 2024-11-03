package com.proxtx.clip.ui.screens.recorder

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.work.WorkManager
import com.proxtx.clip.data.ClipRepository
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proxtx.clip.services.AudioRecorderService

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecorderScreen (modifier: Modifier = Modifier, recorderViewModel: RecorderViewModel = viewModel(factory = RecorderViewModel.Factory)) {
    //recorderViewModel.startRecorder()
    val context = LocalContext.current;
    Button(modifier = modifier, onClick = {
        val intent = Intent(context, AudioRecorderService::class.java)
        context.startForegroundService(intent)
    }) {
        Text("Click Me")
    }
}