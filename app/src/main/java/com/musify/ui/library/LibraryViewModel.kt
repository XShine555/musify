package com.musify.ui.library

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musify.api.Api
import com.musify.model.PlaylistResult
import com.musify.model.RecentlyPlayedTrackResult
import kotlinx.coroutines.launch

class LibraryViewModel : ViewModel() {
    private val _playlists = MutableLiveData(emptyList<PlaylistResult>())
    val playlists: LiveData<List<PlaylistResult>> = _playlists

    private val _recentlyPlayedTracks = MutableLiveData(emptyList<RecentlyPlayedTrackResult>())
    val recentlyPlayedTracks: LiveData<List<RecentlyPlayedTrackResult>> = _recentlyPlayedTracks

    private val _errorMessage = MutableLiveData<String?>(null)

    val errorMessage: LiveData<String?> = _errorMessage

    fun addPlaylist() {
        viewModelScope.launch {
            try {
                val response = Api.getPlaylistService().addPlaylist()
                if (response.isSuccessful) {
                    val currentPlaylists = _playlists.value ?: emptyList()
                    _playlists.value = currentPlaylists + response.body()!!
                } else {
                    _errorMessage.value = "Failed to add playlist."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred. Please try again."
                Log.e("LibraryViewModel", "Error adding playlist", e)
            }
        }
    }

    fun loadPlaylists() {
        viewModelScope.launch {
            try {
                val response = Api.getPlaylistService().getPlaylists()
                if (response.isSuccessful) {
                    _playlists.value = response.body()
                } else {
                    _errorMessage.value = "Failed to load playlists."
                }
            } catch (e: Exception) {
                _errorMessage.value = "An error occurred. Please try again."
                Log.e("LibraryViewModel", "Error loading playlists", e)
            }
        }
    }
}