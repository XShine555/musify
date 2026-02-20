package com.musify.model

data class Track(
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val duration: String, // e.g., "3:45"
    val imageUrl: String
)