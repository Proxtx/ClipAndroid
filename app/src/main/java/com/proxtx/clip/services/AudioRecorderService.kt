package com.proxtx.clip.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.getSystemService
import androidx.work.ListenableWorker.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

const val NOTIFICATION_CHANNEL_ID = "RECORDER_SERVICE"
const val TAG = "RECORDER_SERVICE"

class AudioRecorderService: Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()

        scope.launch {
            recorder()
        }

        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private suspend fun recorder () {
        try {
            var recorder = MediaRecorder(this);
            recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_2_TS)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            recorder.setOutputFile(File(applicationContext.filesDir.path.plus("media.ogg")))
            recorder.prepare()
            recorder.start()
            delay(5_000)
            recorder.stop()
            recorder.release()
        }
        catch (e: Exception){
            e.printStackTrace()
            Log.e(TAG, e.toString()?:e.toString())
            throw e
        }
    }

    private fun startForeground() {
        val micPermission = PermissionChecker.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)

        if (micPermission != PermissionChecker.PERMISSION_GRANTED) {

            stopSelf()
            return
        }

        try {
            val channel = NotificationChannelCompat.Builder(NOTIFICATION_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_LOW).setName("Recorder Service").setDescription("Notifications related to the recorder Service").build()
            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.createNotificationChannel(channel)


            val notification = NotificationCompat.Builder(this, "RECORDER_SERVICE").build()
            ServiceCompat.startForeground(
                this,
                100,
                notification,
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_MICROPHONE
                } else {
                    0
                }
            )
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null
}