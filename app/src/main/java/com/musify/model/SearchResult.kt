package com.musify.model

data class SearchResult(
    val id: Int, val title: String, val imageUrl: String, val subtitle: String, val type: SearchResultType
)