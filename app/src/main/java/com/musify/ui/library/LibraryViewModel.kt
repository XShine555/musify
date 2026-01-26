package com.musify.ui.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musify.model.PlaylistResult
import com.musify.ui.common.PlaylistDataSource

class LibraryViewModel: ViewModel() {
    private val _playlists = MutableLiveData(PlaylistDataSource.items)
    val playlists: LiveData<List<PlaylistResult>> = _playlists

    fun addPlaylist(playlist: PlaylistResult) {
        val currentList = _playlists.value ?: emptyList()
        _playlists.value = currentList + playlist
    }

    fun removePlaylist(playlist: PlaylistResult) {
        val currentList = _playlists.value ?: emptyList()
        _playlists.value = currentList - playlist
    }
}