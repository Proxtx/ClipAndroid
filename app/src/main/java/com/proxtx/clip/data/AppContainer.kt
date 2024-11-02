package com.proxtx.clip.data

import android.content.Context

interface AppContainer {
    val clipRepository: ClipRepository
}

class DefaultAppContainer(context: Context) : AppContainer {
    override val clipRepository = ClipWorkerManagerRepository(context)
}