package com.example.data.feature.settings

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.data.feature.settings.SettingsRepository

internal class SettingsRepositoryImpl(private val context: Context): SettingsRepository {

    private val Context.dataStore by preferencesDataStore("FreeToGamePreferences")
}