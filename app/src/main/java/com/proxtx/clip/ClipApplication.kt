package com.proxtx.clip

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.proxtx.clip.data.AppContainer
import com.proxtx.clip.data.DefaultAppContainer
import com.proxtx.clip.data.ServiceStatusRepository

private const val SERVICE_STATUS_PREFERENCES_NAME = "service_status_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = SERVICE_STATUS_PREFERENCES_NAME
)

class ClipApplication: Application() {
    lateinit var container: AppContainer
    lateinit var serviceStatusRepository: ServiceStatusRepository

    override fun onCreate() {
        super.onCreate()
        serviceStatusRepository = ServiceStatusRepository(dataStore)
        container = DefaultAppContainer(this)
    }
}