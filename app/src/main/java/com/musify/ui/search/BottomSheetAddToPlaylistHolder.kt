package com.musify.ui.search

import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.musify.R
import com.musify.model.PlaylistResult

class BottomSheetAddToPlaylistHolder(
    itemView: View,
    private val listener: OnAddToPlaylistListener
) : RecyclerView.ViewHolder(itemView)  {
    private val title: Button = itemView.findViewById(R.id.playlist_name)

    fun bind(item: PlaylistResult) {
        title.text = item.title
        title.setOnClickListener {
            listener.onAddToPlaylist(item.id)
        }
    }
}