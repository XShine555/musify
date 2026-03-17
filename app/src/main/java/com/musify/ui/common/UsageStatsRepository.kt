package com.musify.ui.common

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.usageDataStore by preferencesDataStore("usage_stats")

data class UsageStats(
    val appLaunches: Int,
    val userScreenViews: Int,
    val trackAdds: Int,
    val trackRemoves: Int,
    val totalUsageMinutes: Int
) {
    val usageHours: Double get() = totalUsageMinutes.toDouble() / 60.0
    val estimatedCo2Kg: Double get() = usageHours * 0.06
}

class UsageStatsRepository(private val context: Context) {
    companion object {
        val APP_LAUNCHES = intPreferencesKey("app_launches")
        val USER_SCREEN_VIEWS = intPreferencesKey("user_screen_views")
        val TRACK_ADDS = intPreferencesKey("track_adds")
        val TRACK_REMOVES = intPreferencesKey("track_removes")
        val TOTAL_USAGE_MINUTES = intPreferencesKey("total_usage_minutes")
    }

    private val dataStore = context.usageDataStore

    fun getUsageStats(): Flow<UsageStats> {
        return dataStore.data.map { prefs ->
            UsageStats(
                appLaunches = prefs[APP_LAUNCHES] ?: 0,
                userScreenViews = prefs[USER_SCREEN_VIEWS] ?: 0,
                trackAdds = prefs[TRACK_ADDS] ?: 0,
                trackRemoves = prefs[TRACK_REMOVES] ?: 0,
                totalUsageMinutes = prefs[TOTAL_USAGE_MINUTES] ?: 0
            )
        }
    }

    suspend fun incrementAppLaunches() {
        updateInt(APP_LAUNCHES, 1)
    }

    suspend fun incrementUserScreenViews() {
        updateInt(USER_SCREEN_VIEWS, 1)
    }

    suspend fun incrementTrackAdds() {
        updateInt(TRACK_ADDS, 1)
    }

    suspend fun incrementTrackRemoves() {
        updateInt(TRACK_REMOVES, 1)
    }

    suspend fun addUsageMinutes(minutes: Int) {
        updateInt(TOTAL_USAGE_MINUTES, minutes)
    }

    private suspend fun updateInt(key: Preferences.Key<Int>, delta: Int) {
        dataStore.edit { prefs ->
            val oldValue = prefs[key] ?: 0
            prefs[key] = oldValue + delta
        }
    }
}
