package com.musify.ui.user

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.model.UserTrackResult

// ViewHolder encargado de enlazar la información de un UserTrackResult.
class UserTracksHolder(
    itemView: View, private val onItemClick: (UserTrackResult) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val trackTitle: TextView = itemView.findViewById(R.id.track_title)
    private val trackImage: ImageView = itemView.findViewById(R.id.track_image)

    fun bind(item: UserTrackResult) {
        trackTitle.text = item.title

        val radius = itemView.context.resources.getDimensionPixelSize(R.dimen.radius_large)
        Glide.with(itemView.context).load(item.imageUrl).centerCrop()
            .placeholder(R.drawable.playlist_placeholder).transform(RoundedCorners(radius))
            .into(trackImage)

        itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}