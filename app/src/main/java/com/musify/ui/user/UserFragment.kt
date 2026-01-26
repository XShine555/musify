package com.musify.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.databinding.FragmentUserBinding

class UserFragment : Fragment() {
    private var _binding: FragmentUserBinding? = null

    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val playlistsAdapter = UserPlaylistsAdapter(
            emptyList(), { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        binding.playlistsList.adapter = playlistsAdapter
        binding.playlistsList.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        viewModel.playlists.observe(viewLifecycleOwner) { playlistResults ->
            playlistsAdapter.updateList(playlistResults)
        }

        val tracksAdapter = UserTracksAdapter(
            emptyList(), { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        binding.tracksLists.adapter = tracksAdapter
        binding.tracksLists.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        viewModel.tracks.observe(viewLifecycleOwner) { trackResults ->
            tracksAdapter.updateList(trackResults)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}