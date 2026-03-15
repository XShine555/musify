package com.musify.api

import com.musify.model.PlaylistResult
import com.musify.model.Track
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistService {
    @Headers("Content-Type: application/json")
    @GET("playlists")
    suspend fun getPlaylists(
        @Header("Authorization") accessToken: String
    ): Response<List<PlaylistResult>>

    @POST("playlists")
    suspend fun addPlaylist(
        @Header("Authorization") accessToken: String
    ): Response<PlaylistResult>

    @GET("playlists/{id}")
    suspend fun getPlaylistById(
        @Header("Authorization") accessToken: String, @Path("id") playlistId: Int
    ): Response<PlaylistResult>

    @DELETE("playlists/{id}")
    suspend fun deletePlaylist(
        @Header("Authorization") accessToken: String, @Path("id") playlistId: Int
    ): Response<Unit>

    @GET("playlists/{id}/tracks")
    suspend fun getTracksInPlaylist(
        @Header("Authorization") accessToken: String,
        @Path("id") playlistId: Int,
        @Query("sortBy") sortBy: String,
        @Query("direction") direction: String
    ): Response<List<Track>>

    @Multipart
    @PUT("playlists/{id}")
    suspend fun updatePlaylist(
        @Header("Authorization") accessToken: String,
        @Path("id") playlistId: Int,
        @Part("title") newTitle: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<PlaylistResult>
}