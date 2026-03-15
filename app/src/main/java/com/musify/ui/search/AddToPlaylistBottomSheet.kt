package com.musify.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.musify.R
import com.musify.model.PlaylistResult

class AddToPlaylistBottomSheet(
    private val playlists: List<PlaylistResult>,
    private val listener: OnAddToPlaylistListener
) : BottomSheetDialogFragment(), OnAddToPlaylistListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.bottom_sheet_add_to_playlist_layout,
            container,
            false
        )

        recyclerView = view.findViewById(R.id.recycler_view_all_playlists)

        val adapter = BottomSheetAddToPlaylistAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.updateList(playlists)

        return view
    }

    override fun onAddToPlaylist(playlistId: Int) {
        listener.onAddToPlaylist(playlistId)
        dismiss()
    }
}