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
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.proxtx.clip.ClipApplication
import com.proxtx.clip.data.ClipRepository
import com.proxtx.clip.services.AudioRecorderService
import kotlinx.coroutines.delay
import java.io.File

class RecorderViewModel(private val clipRepository: ClipRepository) : ViewModel(){
    @RequiresApi(Build.VERSION_CODES.O)
    fun startRecorder(ctx: Context) {
        //val intent = Intent(ctx, AudioRecorderService::class.java)
        //ctx.startForegroundService(intent)
        clipRepository.startRecorder()
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val clipRepository = (this[APPLICATION_KEY] as ClipApplication).container.clipRepository
                RecorderViewModel(clipRepository = clipRepository)
            }
        }
    }
}