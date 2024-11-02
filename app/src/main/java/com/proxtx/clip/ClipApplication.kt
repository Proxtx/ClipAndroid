package com.proxtx.clip

import android.app.Application
import com.proxtx.clip.data.AppContainer
import com.proxtx.clip.data.DefaultAppContainer

class ClipApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(this)
    }
}