package com.frank.han.data.app

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import com.frank.han.data.app.PreferencesKeys.counter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

const val STORE_SETTINGS = "settings"

class AppPrefs(
    private val dataStore: DataStore<Preferences>
) {

    fun getCounter(): Flow<Int> = dataStore.data.map { it[counter] ?: 0 }

    suspend fun increaseCounter() {
        dataStore.edit {
            val cur = it[counter] ?: 0
            println("increaseCounter $cur in thread ${Thread.currentThread().name}")
            it[counter] = cur + 1
        }
    }
}

private object PreferencesKeys {
    val counter = preferencesKey<Int>("counter")
}
