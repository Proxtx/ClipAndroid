package com.proxtx.clip.workers

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay
import java.io.File

private const val TAG = "RecorderWorker"

class RecorderWorker(private val ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    @RequiresApi(Build.VERSION_CODES.S)
    override suspend fun doWork(): Result {

        Log.i(TAG, "worker called")
try {
    var recorder = MediaRecorder(ctx);
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
    return Result.failure()
}
        return Result.success()
    }
}