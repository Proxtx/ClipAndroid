package com.proxtx.clip.ui.screens.recorder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.proxtx.clip.ClipApplication
import com.proxtx.clip.data.ClipRepository

class RecorderViewModel(private val clipRepository: ClipRepository) : ViewModel(){
    fun startRecorder() {
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