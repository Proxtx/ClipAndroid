package com.proxtx.clip.workers

import android.app.NotificationManager
import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.getSystemService
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import java.io.File


const val NOTIFICATION_CHANNEL_ID = "RECORDER_SERVICE"
private const val TAG = "RecorderWorker"

class RecorderWorker(private val ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    private val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun doWork(): Result {
        setForeground(createForegroundInfo())

        Log.i(TAG, "worker called")
        try {
            val recorder = MediaRecorder()
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            recorder.setAudioEncodingBitRate(16*44100)
            recorder.setAudioSamplingRate(44100)
            recorder.setOutputFile(File(applicationContext.filesDir,"media.m4a"))
            recorder.prepare()
            recorder.start()
            delay(5_000)
            recorder.stop()
            recorder.release()
        }
        catch (e: Exception){
            e.printStackTrace()
            return Result.failure()
        }
        return Result.success()
    }

    private fun createForegroundInfo(): ForegroundInfo {
        val id = NOTIFICATION_CHANNEL_ID
        val intent = WorkManager.getInstance(ctx).createCancelPendingIntent(getId())

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val notification = NotificationCompat.Builder(ctx, id).setOngoing(true).addAction(android.R.drawable.ic_media_pause, "Cancel", intent).setSmallIcon(android.R.drawable.ic_notification_overlay).build()

        return ForegroundInfo(1000, notification)
    }

    private fun createChannel() {
        val channel = NotificationChannelCompat.Builder(com.proxtx.clip.services.NOTIFICATION_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_LOW).setName("Recorder Service").setDescription("Notifications related to the recorder Service").build()
        val notificationManager = NotificationManagerCompat.from(ctx)
        notificationManager.createNotificationChannel(channel)
    }
}