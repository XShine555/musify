package com.musify.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musify.R
import com.musify.model.UserTrackResult

// Adapter encargado de mostrar la lista de canciones en el RecyclerView.
class UserTracksAdapter (
    private var items: List<UserTrackResult>, private val onItemClick: (UserTrackResult) -> Unit
) : RecyclerView.Adapter<UserTracksHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserTracksHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user_track, parent, false)
        return UserTracksHolder(view, onItemClick)
    }

    override fun onBindViewHolder(
        holder: UserTracksHolder,
        position: Int
    ) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateList(newList: List<UserTrackResult>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
}