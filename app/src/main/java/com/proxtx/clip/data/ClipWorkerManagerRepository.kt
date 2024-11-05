package com.proxtx.clip.data

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.core.app.ActivityManagerCompat
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.proxtx.clip.services.AudioRecorderService
import java.util.concurrent.TimeUnit

class ClipWorkerManagerRepository(private val context: Context): ClipRepository {
    private val workManager = WorkManager.getInstance(context)

    override fun startRecorder() {
//        val recorderWorker = OneTimeWorkRequestBuilder<RecorderWorker>().setConstraints(Constraints.Builder().setRequiresDeviceIdle(false).build())
        val intent = Intent(context, AudioRecorderService::class.java)
        //intent.setAction()
        context.startForegroundService(intent)
        //workManager.enqueue(recorderWorker.build())
        //val recorderWorker = PeriodicWorkRequestBuilder<RecorderWorker>(6, TimeUnit.SECONDS).build()
        //workManager.enqueue(recorderWorker)
    }

    override fun stopRecorder() {
        val intent = Intent(context, AudioRecorderService::class.java)
        context.stopService(intent)
    }
}