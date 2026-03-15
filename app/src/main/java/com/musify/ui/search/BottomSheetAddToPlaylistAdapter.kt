package com.musify.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.musify.R
import com.musify.model.PlaylistResult

class BottomSheetAddToPlaylistAdapter(
    private val listener: OnAddToPlaylistListener
)
    : RecyclerView.Adapter<BottomSheetAddToPlaylistHolder>(){
        private var items: List<PlaylistResult> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BottomSheetAddToPlaylistHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.bottom_sheet_add_to_playlist_item_layout, parent, false)
        return BottomSheetAddToPlaylistHolder(view, listener)
    }

    override fun onBindViewHolder(
        holder: BottomSheetAddToPlaylistHolder,
        position: Int
    ) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateList(newList: List<PlaylistResult>) {
        items = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return items.size
    }
}