package com.musify.ui.library

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musify.model.PlaylistItem

class LibraryViewModel : ViewModel() {
    private val _playlists = MutableLiveData(
        listOf(
            PlaylistItem(
                1,
                "Un Verano Sin Ti",
                "Bad Bunny",
                "https://i.scdn.co/image/ab67616d0000b27349d694203245f241a1bcaa72"
            ), PlaylistItem(
                2,
                "DtMF",
                "Bad Bunny",
                "https://images.genius.com/3b00c9850faf570161794846d22e0611.1000x1000x1.png"
            ), PlaylistItem(
                3,
                "Donde quiero estar",
                "Quevedo",
                "https://i.scdn.co/image/ab67616d00001e02d53036b726a5a5a1cda5e891"
            )
        )
    )

    val playlists: MutableLiveData<List<PlaylistItem>> = MutableLiveData(_playlists.value.orEmpty())

    fun addPlaylist(playlist: PlaylistItem) {
        val updatedList = _playlists.value.orEmpty().toMutableList()
        updatedList.add(playlist)
        _playlists.value = updatedList
        playlists.value = _playlists.value
    }

    fun removePlaylist(id: Int) {
        val current = _playlists.value.orEmpty()
        _playlists.value = current.filter { it.id != id }
        playlists.value = _playlists.value
    }

    fun searchPlaylists(query: String) {
        playlists.value = _playlists.value.orEmpty().filter {
            it.title.contains(query, ignoreCase = true) || it.owner.contains(
                query, ignoreCase = true
            )
        }
    }
}
