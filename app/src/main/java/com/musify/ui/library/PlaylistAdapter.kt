package com.musify.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musify.R
import com.musify.model.PlaylistItem

class PlaylistAdapter(
    private var items: List<PlaylistItem>, private val onItemClick: (PlaylistItem) -> Unit
) : RecyclerView.Adapter<PlaylistHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_library_playlist, parent, false)
        return PlaylistHolder(view, onItemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PlaylistHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateList(newList: List<PlaylistItem>) {
        items = newList
        notifyDataSetChanged()
    }
}