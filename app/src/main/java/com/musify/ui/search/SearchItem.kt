package com.musify.ui.search

// Clase de datos para cada resultado de búsqueda
data class SearchItem(
    val name: String,
    val imageUrl: String,
    val type: String   // "song" o "user"
)
