package com.proxtx.clip.ui.screens.recorder

import android.content.Context
import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.proxtx.clip.ClipApplication
import com.proxtx.clip.data.ClipRepository
import com.proxtx.clip.data.ServiceStatusRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File

data class RecorderUiState(
    val isServiceRunning: Boolean = false
)

class RecorderViewModel(private val clipRepository: ClipRepository, private val serviceStatusRepository: ServiceStatusRepository) : ViewModel(){
    val uiState: StateFlow<RecorderUiState> = serviceStatusRepository.isServiceRunning.map { isServiceRunning ->
        RecorderUiState(isServiceRunning)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = RecorderUiState()
    )

    private fun startRecorder() {
        //val intent = Intent(ctx, AudioRecorderService::class.java)
        //ctx.startForegroundService(intent)
        clipRepository.startRecorder()
    }

    private fun stopRecorder() {
        clipRepository.stopRecorder()
    }

    fun toggleRecorder(state: Boolean) {
        if (uiState.value.isServiceRunning && !state) {
            stopRecorder()
        } else if (!uiState.value.isServiceRunning && state) {
            startRecorder()
        }
    }

    fun resetRecorder() {
        viewModelScope.launch {
            serviceStatusRepository.updateServiceStatus(false)
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as ClipApplication)
                val clipRepository = application.container.clipRepository
                val serviceStatusRepository = application.serviceStatusRepository
                RecorderViewModel(clipRepository = clipRepository, serviceStatusRepository = serviceStatusRepository)
            }
        }
    }
}