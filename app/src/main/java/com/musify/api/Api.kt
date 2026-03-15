package com.musify.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {
        private val BASE_URL = "http://79.72.50.212/"

        private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder().addInterceptor(logging).build()

        private var playlistService: PlaylistService? = null
        private var trackService: TrackService? = null
        private var userService: UserService? = null

        @Synchronized
        fun getPlaylistService(): PlaylistService {
            if (playlistService == null) {
                playlistService = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()
                    .create(PlaylistService::class.java)
            }
            return playlistService!!
        }

        @Synchronized
        fun getTrackService(): TrackService {
            if (trackService == null) {
                trackService = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()
                    .create(TrackService::class.java)
            }
            return trackService!!
        }

        @Synchronized
        fun getUserService(): UserService {
            if (userService == null) {
                userService = Retrofit.Builder().baseUrl(BASE_URL).client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build()
                    .create(UserService::class.java)
            }
            return userService!!
        }
    }
}