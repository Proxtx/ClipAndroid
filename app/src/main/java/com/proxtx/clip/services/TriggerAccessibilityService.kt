package com.proxtx.clip.services

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

const val AC_TAG = "AccessibilityService";

class TriggerAccessibilityService : AccessibilityService() {
    private lateinit var vibrator: Vibrator

    override fun onServiceConnected() {
        super.onServiceConnected()

        vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {

    }

    override fun onInterrupt() {

    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if(event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> {
                    vibratePhone()
                    Log.i(AC_TAG, "Volume Up clicked")
                }
                KeyEvent.KEYCODE_VOLUME_DOWN -> {

                }
            }
        }

        return super.onKeyEvent(event)
    }

    private fun vibratePhone() {
        // Use VibrationEffect for API 26+
        Log.i(AC_TAG, "Vibrating Phone...")
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        Log.i(AC_TAG, "Phone vibrated...")
    }
}