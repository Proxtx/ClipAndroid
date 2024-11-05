package com.proxtx.clip.workers

import android.content.Context
import android.content.Intent
import androidx.annotation.RestrictTo
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.work.impl.utils.ForceStopRunnable.BroadcastReceiver
import com.proxtx.clip.ClipApplication

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        return
        if(intent != null && intent.action == Intent.ACTION_BOOT_COMPLETED) {
            (context.applicationContext as ClipApplication).container.clipRepository.startRecorder()
        }
    }
}