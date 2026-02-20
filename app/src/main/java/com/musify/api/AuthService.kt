package com.musify.api

import com.musify.model.AuthRequest
import com.musify.model.AuthResult
import com.musify.model.UserResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @GET("auth/me")
    suspend fun getCurrentUser(): Response<UserResult>

    @Headers("Content-Type: application/json")
    @POST("auth/signIn")
    suspend fun signIn(@Body authRequest: AuthRequest): Response<AuthResult>

    @Headers("Content-Type: application/json")
    @POST("auth/signUp")
    suspend fun signUp(@Body authRequest: AuthRequest): Response<AuthResult>
}