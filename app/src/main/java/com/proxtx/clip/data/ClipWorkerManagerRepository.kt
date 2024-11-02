package com.proxtx.clip.data

import android.content.Context
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.proxtx.clip.workers.RecorderWorker

class ClipWorkerManagerRepository(context: Context): ClipRepository {
    private val workManager = WorkManager.getInstance(context)

    override fun startRecorder() {
        val recorderWorker = OneTimeWorkRequestBuilder<RecorderWorker>()

        workManager.enqueue(recorderWorker.build())
    }
}