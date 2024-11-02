package com.proxtx.clip.ui.screens.recorder

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.work.WorkManager
import com.proxtx.clip.data.ClipRepository
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RecorderScreen (recorderViewModel: RecorderViewModel = viewModel(factory = RecorderViewModel.Factory)) {
    recorderViewModel.startRecorder()

}