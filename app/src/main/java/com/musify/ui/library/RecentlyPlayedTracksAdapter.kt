package com.musify.ui.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musify.R
import com.musify.model.RecentlyPlayedTrackResult

class RecentlyPlayedTracksAdapter(
    private var items: List<RecentlyPlayedTrackResult>, private val onItemClick: (RecentlyPlayedTrackResult) -> Unit
) : RecyclerView.Adapter<RecentlyPlayedTracksHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyPlayedTracksHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_recently_played_track, parent, false)
        return RecentlyPlayedTracksHolder(view, onItemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecentlyPlayedTracksHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateList(newList: List<RecentlyPlayedTrackResult>) {
        items = newList
        notifyDataSetChanged()
    }
}