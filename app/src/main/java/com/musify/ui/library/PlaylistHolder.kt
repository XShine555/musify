package com.musify.ui.library

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.model.PlaylistItem

class PlaylistHolder(
    itemView: View, private val onItemClick: (PlaylistItem) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val playlistTitle: TextView = itemView.findViewById(R.id.title)
    private val playlistOwner: TextView = itemView.findViewById(R.id.owner)
    private val playlistImage: ImageView = itemView.findViewById(R.id.image)

    fun bind(item: PlaylistItem) {
        playlistTitle.text = item.title
        playlistOwner.text = item.owner

        Glide.with(itemView.context).load(item.imageUrl).centerCrop()
            .placeholder(R.drawable.playlist_placeholder).transform(RoundedCorners(16))
            .into(playlistImage)

        itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}