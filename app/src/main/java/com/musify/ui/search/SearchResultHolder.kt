package com.musify.ui.search

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.model.SearchResult
import com.musify.model.SearchResultType

// ViewHolder encargado de enlazar la información de un SearchResult.
class SearchResultHolder(
    itemView: View, private val onItemClick: (SearchResult) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    private val searchTitle: TextView = itemView.findViewById(R.id.search_result_title)
    private val searchImage: ImageView = itemView.findViewById(R.id.search_result_image)
    private val searchSubtitle: TextView = itemView.findViewById(R.id.search_result_subtitle)

    fun bind(item: SearchResult) {
        searchTitle.text = item.title
        searchSubtitle.text = when (item.type) {
            SearchResultType.USER -> itemView.context.getString(R.string.user)
            SearchResultType.TRACK -> String.format(
                "%s, %s",
                itemView.context.getString(R.string.track),
                item.subtitle,
            )
        }

        if (item.type == SearchResultType.USER) {
            Glide.with(itemView.context).load(item.imageUrl)
                .placeholder(R.drawable.playlist_placeholder).circleCrop().into(searchImage)
        } else {
            val radius = itemView.context.resources.getDimensionPixelSize(R.dimen.radius_large)
            Glide.with(itemView.context).load(item.imageUrl)
                .placeholder(R.drawable.playlist_placeholder).transform(RoundedCorners(radius))
                .into(searchImage)
        }


        itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}