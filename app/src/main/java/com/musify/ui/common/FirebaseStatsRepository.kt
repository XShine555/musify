package com.musify.ui.common

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirebaseStatsRepository(private val context: Context) {
    private val TAG = "FirebaseStatsRepo"
    private val firestore: FirebaseFirestore? = try {
        FirebaseApp.initializeApp(context)
        FirebaseFirestore.getInstance()
    } catch (e: Exception) {
        Log.w(TAG, "Firebase initialization failed", e)
        null
    }

    suspend fun saveStats(stats: UsageStats) {
        firestore ?: return
        val doc = mapOf(
            "appLaunches" to stats.appLaunches,
            "userScreenViews" to stats.userScreenViews,
            "trackAdds" to stats.trackAdds,
            "trackRemoves" to stats.trackRemoves,
            "totalUsageMinutes" to stats.totalUsageMinutes,
            "timestamp" to System.currentTimeMillis()
        )
        try {
            firestore.collection("musify_analytics").document("last_stats").set(doc).await()
        } catch (e: Exception) {
            Log.w(TAG, "Failed writing stats", e)
        }
    }

    suspend fun loadLastStats(): UsageStats? {
        firestore ?: return null
        return try {
            val snapshot = firestore.collection("musify_analytics").document("last_stats").get().await()
            if (!snapshot.exists()) return null
            UsageStats(
                appLaunches = (snapshot.getLong("appLaunches") ?: 0L).toInt(),
                userScreenViews = (snapshot.getLong("userScreenViews") ?: 0L).toInt(),
                trackAdds = (snapshot.getLong("trackAdds") ?: 0L).toInt(),
                trackRemoves = (snapshot.getLong("trackRemoves") ?: 0L).toInt(),
                totalUsageMinutes = (snapshot.getLong("totalUsageMinutes") ?: 0L).toInt()
            )
        } catch (e: Exception) {
            Log.w(TAG, "Failed reading stats", e)
            null
        }
    }
}
