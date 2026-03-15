package com.musify.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musify.api.Api
import com.musify.model.PlaylistResult
import com.musify.model.SearchResult
import kotlinx.coroutines.launch
import kotlin.collections.emptyList

class SearchViewModel: ViewModel() {
    private val _searchs = MutableLiveData(emptyList<SearchResult>())
    val searchs: LiveData<List<SearchResult>> = _searchs

    private val _playlists = MutableLiveData(emptyList<PlaylistResult>())
    val playlists: LiveData<List<PlaylistResult>> = _playlists

    private val _errorMessage = MutableLiveData<String?>(null)
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadAll() {
        viewModelScope.launch {
            val resultsUsers = Api.getUserService().getUsersSearch()
            val resultsTracks = Api.getTrackService().getTracksSearch()

            if (!resultsUsers.isSuccessful || !resultsTracks.isSuccessful) {
                _errorMessage.value = "Failed to load search results. Please try again."
                return@launch
            }

            val combinedResults = mutableListOf<SearchResult>()
            resultsUsers.body()?.let { combinedResults.addAll(it) }
            resultsTracks.body()?.let { combinedResults.addAll(it) }
            _searchs.value = combinedResults
        }
    }

    fun loadPlaylists() {
        viewModelScope.launch {
            val result = Api.getPlaylistService().getPlaylists()
            if (result.isSuccessful) {
                _playlists.value = result.body() ?: emptyList()
            } else {
                _errorMessage.value = "Failed to load playlists. Please try again."
            }
        }
    }

    fun addTrackToPlaylist(trackId: Int, playlistId: Int) {
        viewModelScope.launch {
            val result = Api.getPlaylistService().addTrackToPlaylist(playlistId, trackId)
            if (!result.isSuccessful) {
                _errorMessage.value = "Failed to add track to playlist. Please try again."
            }
        }
    }
}