package com.musify.ui.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.musify.api.Api
import com.musify.model.PlaylistResult
import com.musify.model.Track
import com.musify.model.TrackSortField
import kotlinx.coroutines.launch

class PlaylistDetailsViewModel : ViewModel() {
    private val _playlistDetails = MutableLiveData<PlaylistResult>()
    val playlistDetails: LiveData<PlaylistResult> = _playlistDetails

    private val _tracks = MutableLiveData<List<Track>>(emptyList())
    val tracks: LiveData<List<Track>> = _tracks

    private val _errorMessage = MutableLiveData<String?>(null)

    val errorMessage: LiveData<String?> = _errorMessage

    private val _deletePlaylistSuccess = MutableLiveData<Boolean>()
    val deletePlaylistSuccess: LiveData<Boolean> = _deletePlaylistSuccess

    fun loadPlaylistDetails(playlistId: Int, accessToken: String) {
        viewModelScope.launch {
            val result = Api.getPlaylistService().getPlaylistById("Bearer $accessToken", playlistId)
            if (result.isSuccessful) {
                val playlistDetails = result.body()
                if (playlistDetails == null) {
                    _errorMessage.value = "Playlist details not found."
                    return@launch
                }

                _playlistDetails.value = playlistDetails
            } else {
                _errorMessage.value = "Failed to load playlist details, please try again later."
            }
        }
    }

    fun loadTracks(
        playlistId: Int,
        accessToken: String,
        sortBy: TrackSortField,
        isAscending: Boolean
    ) {
        viewModelScope.launch {
            val sortByParam = when (sortBy) {
                TrackSortField.TITLE -> "title"
                TrackSortField.ARTIST -> "artist"
                TrackSortField.DATE_ADDED -> "addedAt"
                TrackSortField.DURATION -> "duration"
            }

            val result = Api.getPlaylistService().getTracksInPlaylist(
                "Bearer $accessToken",
                playlistId,
                sortByParam,
                if (isAscending) "asc" else "desc"
            )

            if (result.isSuccessful) {
                val tracks = result.body()
                _tracks.value = tracks ?: emptyList()
            } else {
                _errorMessage.value = "Failed to load tracks, please try again later."
            }
        }
    }

    fun deletePlaylist(playlistId: Int, accessToken: String) {
        viewModelScope.launch {
            val result = Api.getPlaylistService().deletePlaylist("Bearer $accessToken", playlistId)
            if (result.isSuccessful) {
                _deletePlaylistSuccess.value = true
            } else {
                _errorMessage.value = "Failed to delete playlist, please try again later."
            }
        }
    }
}