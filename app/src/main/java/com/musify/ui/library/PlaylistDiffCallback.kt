package com.musify.ui.library

import androidx.recyclerview.widget.DiffUtil
import com.musify.model.PlaylistItem

class PlaylistDiffCallback : DiffUtil.ItemCallback<PlaylistItem>() {
    override fun areItemsTheSame(oldItem: PlaylistItem, newItem: PlaylistItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlaylistItem, newItem: PlaylistItem): Boolean {
        return oldItem == newItem
    }
}