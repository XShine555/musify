package com.musify.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    companion object {
        private val BASE_URL = "http://api.ikerdemo.cat/"

        private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

        private val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        private val client = OkHttpClient.Builder().addInterceptor(logging).build()

        private var authService: AuthService? = null
        private var playlistService: PlaylistService? = null

        @Synchronized
        fun getAuthService(): AuthService {
            if (authService == null) {
                authService =
                    Retrofit.Builder().baseUrl(BASE_URL).client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build()
                        .create(AuthService::class.java)
            }
            return authService!!
        }

        @Synchronized
        fun getPlaylistService(): PlaylistService {
            if (playlistService == null) {
                playlistService =
                    Retrofit.Builder().baseUrl(BASE_URL).client(client)
                        .addConverterFactory(GsonConverterFactory.create(gson)).build()
                        .create(PlaylistService::class.java)
            }
            return playlistService!!
        }
    }
}