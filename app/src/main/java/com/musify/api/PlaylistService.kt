package com.musify.api

import com.musify.model.PlaylistResult
import com.musify.model.Track
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaylistService {
    @GET("playlists")
    suspend fun getPlaylists(): Response<List<PlaylistResult>>

    @POST("playlists")
    suspend fun addPlaylist(): Response<PlaylistResult>

    @GET("playlists/{id}")
    suspend fun getPlaylistById(@Path("id") playlistId: Int): Response<PlaylistResult>

    @DELETE("playlists/{id}")
    suspend fun deletePlaylist(@Path("id") playlistId: Int): Response<Unit>

    @GET("playlists/{id}/tracks")
    suspend fun getTracksInPlaylist(
        @Path("id") playlistId: Int,
        @Query("sortBy") sortBy: String,
        @Query("direction") direction: String
    ): Response<List<Track>>

    @POST("playlists/{id}/tracks")
    suspend fun addTrackToPlaylist(
        @Path("id") playlistId: Int,
        @Query("trackId") trackId: Int
    ): Response<Unit>

    @DELETE("playlists/{playlistId}/tracks/{trackId}")
    suspend fun removeTrackFromPlaylist(
        @Path("playlistId") playlistId: Int,
        @Path("trackId") trackId: Int
    ): Response<Unit>

    @Multipart
    @PUT("playlists/{id}")
    suspend fun updatePlaylist(
        @Path("id") playlistId: Int,
        @Part("title") newTitle: RequestBody,
        @Part image: MultipartBody.Part?
    ): Response<PlaylistResult>
}