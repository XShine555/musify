package com.musify.ui.playlist

import androidx.recyclerview.widget.RecyclerView
import com.musify.databinding.ItemSongBinding
import com.musify.model.Track

class PlaylistTracksViewHolder(
    private val binding: ItemSongBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(track: Track) {
        binding.songTitle.text = track.title
        binding.songArtist.text = track.artist
        binding.songDuration.text = track.duration

        // TODO: Load image with Glide or similar
        // Glide.with(binding.root.context).load(track.imageUrl).into(binding.songImage)

        binding.btnMoreSong.setOnClickListener {
            // TODO: Show options menu
        }

        binding.root.setOnClickListener {
            // TODO: Play track
        }
    }
}