package com.musify.model

data class Track(
    val id: Int,
    val title: String,
    val artist: UserResult,
    val duration: String,
    val imageUrl: String
)