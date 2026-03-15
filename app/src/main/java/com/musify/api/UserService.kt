package com.musify.api

import com.musify.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET

interface UserService {
    @GET("users/")
    suspend fun getUsersSearch(): Response<List<SearchResult>>
}