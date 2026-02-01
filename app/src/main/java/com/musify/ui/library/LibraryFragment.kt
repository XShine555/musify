package com.musify.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.R
import com.musify.databinding.FragmentLibraryBinding
import com.musify.ui.common.HorizontalSpaceItemDecoration
import com.musify.ui.common.VerticalSpaceItemDecoration

class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LibraryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val playlistAdapter = PlaylistResultAdapter(
            emptyList(), { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        val playlistSpacing = resources.getDimensionPixelSize(R.dimen.item_margin_medium)
        binding.playlistList.addItemDecoration(HorizontalSpaceItemDecoration(playlistSpacing))

        binding.playlistList.adapter = playlistAdapter
        binding.playlistList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.playlists.observe(viewLifecycleOwner) { playlistResults ->
            playlistAdapter.updateList(playlistResults)
        }

        val recentlyPlayedTracksAdapter = RecentlyPlayedTracksAdapter(
            emptyList(), { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        val recentlyPlayedTracksSpacing =
            resources.getDimensionPixelSize(R.dimen.item_margin_medium)
        binding.recentlyPlayedTracksList.addItemDecoration(
            VerticalSpaceItemDecoration(
                recentlyPlayedTracksSpacing
            )
        )

        binding.recentlyPlayedTracksList.adapter = recentlyPlayedTracksAdapter
        binding.recentlyPlayedTracksList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.recentlyPlayedTracks.observe(viewLifecycleOwner) { trackResults ->
            recentlyPlayedTracksAdapter.updateList(trackResults)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
