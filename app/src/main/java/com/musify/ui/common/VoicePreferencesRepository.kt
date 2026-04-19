package com.musify.ui.common

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "voice_preferences")

class VoicePreferencesRepository(private val context: Context) {
    companion object {
        private val VOICE_ENABLED = booleanPreferencesKey("voice_enabled")
    }

    val isVoiceEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[VOICE_ENABLED] ?: false
    }

    suspend fun setVoiceEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[VOICE_ENABLED] = enabled
        }
    }
}

