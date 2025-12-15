package com.musify.ui.library

import com.musify.model.PlaylistItem

object LibraryDataSource {
    val items: List<PlaylistItem> = listOf(
        PlaylistItem(
            1,
            "Un Verano Sin Ti",
            "User",
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
}