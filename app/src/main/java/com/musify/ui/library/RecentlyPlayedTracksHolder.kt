package com.musify.ui.library

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.model.RecentlyPlayedTrackResult

class RecentlyPlayedTracksHolder(
    itemView: View, private val onItemClick: (RecentlyPlayedTrackResult) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val trackTitle: TextView = itemView.findViewById(R.id.track_title)
    private val trackOwner: TextView = itemView.findViewById(R.id.track_owner_name)
    private val trackImage: ImageView = itemView.findViewById(R.id.track_image)

    fun bind(item: RecentlyPlayedTrackResult) {
        trackTitle.text = item.title
        trackOwner.text = item.owner

        val radius = itemView.context.resources.getDimensionPixelSize(R.dimen.radius_large)
        Glide.with(itemView.context).load(item.imageUrl).centerCrop()
            .placeholder(R.drawable.playlist_placeholder).transform(RoundedCorners(radius))
            .into(trackImage)

        itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}