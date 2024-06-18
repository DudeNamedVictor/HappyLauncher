package com.example.happylauncher.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import javax.inject.Inject


class DataStoreRepository @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>
) {

    suspend fun <T> getData(key: Preferences.Key<T>, onGetTransparent: (Float?) -> Unit) {
        settingsDataStore.data.collect {
            onGetTransparent.invoke(it[key] as Float?)
        }
    }

    suspend fun update(step: Float) {
        settingsDataStore.edit { it[TRANSPARENT] = step }
    }

    companion object {
        val TRANSPARENT = floatPreferencesKey("transparent")
    }
}