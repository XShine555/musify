package com.musify.ui.playlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musify.databinding.ItemSongBinding
import com.musify.model.Track

class PlaylistTracksAdapter(
    private val onTrackRemoved: (Track) -> Unit
) : RecyclerView.Adapter<PlaylistTracksHolder>() {
    private var tracks: List<Track> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistTracksHolder {
        val binding = ItemSongBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PlaylistTracksHolder(onTrackRemoved, binding)
    }

    override fun onBindViewHolder(holder: PlaylistTracksHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int = tracks.size

    fun updateList(newList: List<Track>) {
        tracks = newList
        notifyDataSetChanged()
    }
}