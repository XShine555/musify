package com.musify.ui.playlist

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.databinding.ItemSongBinding
import com.musify.model.Track

class PlaylistTracksHolder(
    binding: ItemSongBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val trackTitle = binding.trackTitle
    private val trackOwnerName = binding.trackOwnerName
    private val trackImage = binding.trackImage

    fun bind(track: Track) {
        trackTitle.text = track.title
        trackOwnerName.text = track.artist.username

        val radius = itemView.context.resources.getDimensionPixelSize(R.dimen.radius_large)
        Glide.with(itemView.context).load(track.imageUrl).centerCrop()
            .placeholder(R.drawable.img_playlist_placeholder).transform(RoundedCorners(radius)).into(trackImage)
    }
}