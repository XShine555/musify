package com.musify.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musify.api.Api
import com.musify.model.PlaylistResult
import com.musify.model.RecentlyPlayedTrackResult
import kotlinx.coroutines.launch

class LibraryViewModel: ViewModel() {
    private val _playlists = MutableLiveData(emptyList<PlaylistResult>())
    val playlists: LiveData<List<PlaylistResult>> = _playlists

    private val _recentlyPlayedTracks = MutableLiveData(emptyList<RecentlyPlayedTrackResult>())
    val recentlyPlayedTracks: LiveData<List<RecentlyPlayedTrackResult>> = _recentlyPlayedTracks

    private val _errorMessage = MutableLiveData<String?>(null)

    val errorMessage: LiveData<String?> = _errorMessage

    fun addPlaylist(accessToken: String) {
        viewModelScope.launch {
            try {
                val response = Api.getPlaylistService().addPlaylist("Bearer $accessToken")
                if (response.isSuccessful) {
                    val currentPlaylists = _playlists.value ?: emptyList()
                    _playlists.value = currentPlaylists + response.body()!!
                } else {
                    _errorMessage.value = "Failed to add playlist."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred. Please try again."
            }
        }
    }

    fun loadPlaylists(accessToken: String) {
        viewModelScope.launch {
            try {
                val response = Api.getPlaylistService().getPlaylists("Bearer $accessToken")
                if (response.isSuccessful) {
                    _playlists.value = response.body()
                } else {
                    _errorMessage.value = "Failed to load playlists."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred. Please try again."
            }
        }
    }
}