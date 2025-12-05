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
import com.musify.R

class SearchAdapter(
    private val items: MutableList<SearchItem>
) : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.itemImage)
        val name: TextView = view.findViewById(R.id.itemName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search_result, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        holder.name.text = item.name

        // Cargar la imagen con Glide
        Glide.with(holder.itemView)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_launcher_background) // placeholder local
            .into(holder.img)

        // Forma: circular para usuarios, cuadrada para canciones
        if (item.type == "user") {
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
