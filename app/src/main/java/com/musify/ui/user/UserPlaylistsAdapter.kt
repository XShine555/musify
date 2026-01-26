package com.musify.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musify.R
import com.musify.model.PlaylistResult

// Adapter encargado de mostrar la lista de Playlists en el RecyclerView.
class UserPlaylistsAdapter(
    private var items: List<PlaylistResult>, private val onItemClick: (PlaylistResult) -> Unit
) : RecyclerView.Adapter<UserPlaylistsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserPlaylistsHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_user_playlist, parent, false)
        return UserPlaylistsHolder(view, onItemClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: UserPlaylistsHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateList(newList: List<PlaylistResult>) {
        items = newList
        notifyDataSetChanged()
    }
}