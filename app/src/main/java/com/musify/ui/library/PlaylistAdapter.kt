package com.musify.ui.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.model.PlaylistItem

class PlaylistAdapter(
    private val onItemClick: ((PlaylistItem) -> Unit)? = null
) : ListAdapter<PlaylistItem, PlaylistAdapter.PlaylistViewHolder>(PlaylistDiffCallback()) {

    inner class PlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.playlistImage)
        private val title: TextView = itemView.findViewById(R.id.playlistTitle)
        private val owner: TextView = itemView.findViewById(R.id.playlistOwner)

        fun bind(item: PlaylistItem) {
            title.text = item.title
            owner.text = item.owner

            Glide.with(itemView.context).load(item.imageUrl).centerCrop()
                .placeholder(R.drawable.playlist_placeholder).transform(RoundedCorners(16))
                .into(image)

            itemView.setOnClickListener {
                onItemClick?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.library_item_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}