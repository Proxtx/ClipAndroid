package com.proxtx.clip.services

import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.media.EncoderProfiles
import android.media.MediaRecorder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.ServiceCompat
import androidx.core.content.PermissionChecker
import com.proxtx.clip.ClipApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Files

const val NOTIFICATION_CHANNEL_ID = "RECORDER_SERVICE"
const val TAG = "RECORDER_SERVICE"

class AudioRecorderService: Service() {
    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground()
        val context = (this.applicationContext as ClipApplication)

        scope.launch {
            context.serviceStatusRepository.updateServiceStatus(true)
            recorder()
        }

        return START_STICKY
    }

    override fun onDestroy() {
        val context = (this.applicationContext as ClipApplication)
        scope.launch {
            context.serviceStatusRepository.updateServiceStatus(false)
        }
        stopForeground(Service.STOP_FOREGROUND_REMOVE)
        stopSelf()
        super.onDestroy()
    }

    private suspend fun recorder () {
        try {
            withContext(Dispatchers.IO) {
                Files.createDirectories(applicationContext.filesDir.toPath().resolve("recordings"))
            }
            while(true) {
                val recorder = MediaRecorder()
                recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
                recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                recorder.setAudioEncodingBitRate(16 * 44_100)
                recorder.setAudioSamplingRate(44_100)
                recorder.setOutputFile(
                    File(
                        applicationContext.filesDir.path.plus("/recordings"),
                        "${System.currentTimeMillis()}.m4a"
                    )
                )
                recorder.prepare()
                recorder.start()
                delay(300_000)
                recorder.stop()
                recorder.release()
            }
        }
        catch (e: Exception){
            e.printStackTrace()
            Log.e(TAG, e.toString()?:e.toString())
            delay(5000)
            recorder()
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