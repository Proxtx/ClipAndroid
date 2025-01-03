package com.proxtx.clip.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class ServiceStatusRepository(private val dataStore: DataStore<Preferences>) {
    val isServiceRunning: Flow<Boolean> = dataStore.data.catch {
        if(it is IOException) {
            Log.e(TAG, "Error reading preferences.", it)
            emit(emptyPreferences())
        } else {
            throw it
        }
    }.map { preferences ->
        preferences[IS_SERVICE_RUNNING] ?: true
    }

    suspend fun updateServiceStatus(isRunning: Boolean) {
        dataStore.edit { preferences->
            preferences[IS_SERVICE_RUNNING] = isRunning
        }
    }

    private companion object {
        val IS_SERVICE_RUNNING = booleanPreferencesKey("is_service_running")
        const val TAG = "ServiceStatusRepository"
    }
}