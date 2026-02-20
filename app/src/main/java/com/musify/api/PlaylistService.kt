package com.musify.api

import com.musify.model.PlaylistResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PlaylistService {
    @Headers("Content-Type: application/json")
    @GET("playlists")
    suspend fun getPlaylists(
        @Header("Authorization") accessToken: String
    ): Response<List<PlaylistResult>>

    @POST("playlists")
    suspend fun addPlaylist(
        @Header("Authorization") accessToken: String
    ):  Response<PlaylistResult>
}