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
import com.musify.model.SearchItem
import com.musify.model.SearchType

class SearchAdapter(
    private val items: MutableList<SearchItem>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.itemImage)
        val name: TextView = view.findViewById(R.id.itemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.search_item_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.name.text = item.name

        // Cargar la imagen.
        Glide.with(holder.itemView)
            .load(item.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.playlist_placeholder)
            .transform(RoundedCorners(16))
            .into(holder.img)

        if (item.type == SearchType.USER) {
            holder.img.background = ShapeDrawable(OvalShape())
            holder.img.clipToOutline = true
        } else {
            holder.img.background = null
            holder.img.clipToOutline = false
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<SearchItem>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}
