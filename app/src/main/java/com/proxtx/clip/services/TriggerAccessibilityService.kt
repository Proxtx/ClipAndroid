package com.proxtx.clip.services

import android.accessibilityservice.AccessibilityService
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class TriggerAccessibilityService : AccessibilityService() {
    override fun onServiceConnected() {
        super.onServiceConnected()

    }

    override fun onAccessibilityEvent(p0: AccessibilityEvent?) {

    }

    override fun onInterrupt() {

    }

    override fun onKeyEvent(event: KeyEvent): Boolean {
        if(event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP -> {

                }
                KeyEvent.KEYCODE_VOLUME_DOWN -> {

                }
            }
        }

        return super.onKeyEvent(event)
    }
}