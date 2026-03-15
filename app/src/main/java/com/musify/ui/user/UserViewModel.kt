package com.musify.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musify.model.PlaylistResult
import com.musify.model.UserTrackResult
import com.musify.ui.common.PlaylistDataSource

class UserViewModel : ViewModel() {
    private val _playlists = MutableLiveData(PlaylistDataSource.items)
    val playlists: LiveData<List<PlaylistResult>> = _playlists

    private val _tracks = MutableLiveData(TrackDataSource.items)
    val tracks: LiveData<List<UserTrackResult>> = _tracks

    fun addPlaylist(PlaylistResult: PlaylistResult) {
        val currentList = _playlists.value ?: emptyList()
        _playlists.value = currentList + PlaylistResult
    }

    fun removePlaylist(PlaylistResult: PlaylistResult) {
        val currentList = _playlists.value ?: emptyList()
        _playlists.value = currentList - PlaylistResult
    }

    fun addTrack(userTrackResult: UserTrackResult) {
        val currentList = _tracks.value ?: emptyList()
        _tracks.value = currentList + userTrackResult
    }

    fun removeTrack(userTrackResult: UserTrackResult) {
        val currentList = _tracks.value ?: emptyList()
        _tracks.value = currentList - userTrackResult
    }
}