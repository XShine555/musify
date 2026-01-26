package com.musify.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.databinding.FragmentUserBinding
import com.musify.ui.common.PlaylistDataSource

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val userPlaylistsAdapter = UserPlaylistsAdapter(
            PlaylistDataSource.items, { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        binding.playlistsList.adapter = userPlaylistsAdapter
        binding.playlistsList.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )

        val userTracksAdapter = UserTracksAdapter(
            TrackDataSource.items, { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        binding.tracksLists.adapter = userTracksAdapter
        binding.tracksLists.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}