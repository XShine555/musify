package com.musify.api

import com.musify.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET

interface TrackService {
    @GET("tracks/")
    suspend fun getTracksSearch(): Response<List<SearchResult>>
}