package com.musify.ui.user

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.model.PlaylistResult

// ViewHolder encargado de enlazar la información de un PlaylistResult.
class UserPlaylistsHolder(
    itemView: View, private val onItemClick: (PlaylistResult) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val playlistTitle: TextView = itemView.findViewById(R.id.playlist_title)
    private val playlistImage: ImageView = itemView.findViewById(R.id.playlist_image)

    fun bind(item: PlaylistResult) {
        playlistTitle.text = item.title

        Glide.with(itemView.context).load(item.imageUrl).centerCrop()
            .placeholder(R.drawable.playlist_placeholder).transform(RoundedCorners(16))
            .into(playlistImage)

        itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}