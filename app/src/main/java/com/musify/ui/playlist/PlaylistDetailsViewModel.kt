package com.musify.ui.playlist

import android.util.Log
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

    private val _deleteTrackSuccess = MutableLiveData<Boolean>()
    val deleteTrackSuccess: LiveData<Boolean> = _deleteTrackSuccess

    fun loadPlaylistDetails(playlistId: Int) {
        try {
            viewModelScope.launch {
                val result = Api.getPlaylistService().getPlaylistById(playlistId)
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
        catch (e: Exception) {
            _errorMessage.value = "An error occurred. Please try again."
            Log.e("PlaylistDetails", "Error loading playlist details", e)
        }
    }

    fun loadTracks(
        playlistId: Int, sortBy: TrackSortField, isAscending: Boolean
    ) {
        try {
            viewModelScope.launch {
                val sortByParam = when (sortBy) {
                    TrackSortField.TITLE -> "title"
                    TrackSortField.ARTIST -> "artist"
                    TrackSortField.DATE_ADDED -> "addedAt"
                    TrackSortField.DURATION -> "duration"
                }

                val result = Api.getPlaylistService().getTracksInPlaylist(
                    playlistId, sortByParam, if (isAscending) "asc" else "desc"
                )

                if (result.isSuccessful) {
                    val tracks = result.body()
                    _tracks.value = tracks ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to load tracks, please try again later."
                }
            }
        }
        catch (e: Exception) {
            _errorMessage.value = "An error occurred. Please try again."
            Log.e("PlaylistDetails", "Error loading tracks", e)
        }
    }

    fun deletePlaylist(playlistId: Int) {
        viewModelScope.launch {
            try {
                val result = Api.getPlaylistService().deletePlaylist(playlistId)
                if (result.isSuccessful) {
                    _deletePlaylistSuccess.value = true
                } else {
                    _errorMessage.value = "Failed to delete playlist, please try again later."
                }
            }
            catch (e: Exception) {
                _errorMessage.value = "An error occurred. Please try again."
                Log.e("PlaylistDetails", "Error deleting playlist", e)
            }
        }
    }

    fun removeTrackFromPlaylist(playlistId: Int, trackId: Int) {
        viewModelScope.launch {
            try {
                val response = Api.getPlaylistService().removeTrackFromPlaylist(playlistId, trackId)
                if (!response.isSuccessful) {
                    _errorMessage.value = "Failed to remove track from playlist."
                    _deleteTrackSuccess.value = false
                } else {
                    _deleteTrackSuccess.value = true
                }
            } catch (e: Exception) {
                _deleteTrackSuccess.value = false
                _errorMessage.value = "An error occurred. Please try again."
                Log.e("PlaylistDetails", "Error removing track from playlist", e)
            }
        }
    }
}