package com.absut.jetquotes.data.preference

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DataStoreManager @Inject constructor(app: Application) {

    private val settingsDataStore = app.dataStore

    suspend fun setThemeMode(mode: Int) {
        settingsDataStore.edit { settings ->
            settings[PREF_THEME] = mode
        }
    }

    val themeMode: Flow<Int> = settingsDataStore.data.map { preferences ->
        preferences[PREF_THEME] ?: AppCompatDelegate.MODE_NIGHT_YES
    }

    companion object {
        val PREF_THEME = intPreferencesKey("theme")
    }

}