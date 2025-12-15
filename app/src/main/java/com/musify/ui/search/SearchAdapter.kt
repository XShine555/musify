package com.musify.ui.search

import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.model.SearchResultItem
import com.musify.model.SearchResultType

class SearchAdapter(
    private val items: MutableList<SearchResultItem>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.title)
        val subtitle: TextView = view.findViewById(R.id.subtitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.title.text = item.title

        Glide.with(holder.itemView).load(item.imageUrl).centerCrop()
            .placeholder(R.drawable.playlist_placeholder).transform(RoundedCorners(16))
            .into(holder.img)

        if (item.type == SearchResultType.USER) {
            holder.img.background = ShapeDrawable(OvalShape())
            holder.img.clipToOutline = true
        } else {
            holder.img.background = null
            holder.img.clipToOutline = false
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<SearchResultItem>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}
