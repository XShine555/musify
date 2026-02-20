package com.musify.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.R
import com.musify.databinding.FragmentPlaylistDetailsBinding
import com.musify.model.Track

class PlaylistDetailsFragment : Fragment() {

    private var _binding: FragmentPlaylistDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var tracksAdapter: PlaylistTracksAdapter
    private val tracks = mutableListOf<Track>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupSortSpinner()
        setupButtons()
        loadMockData()
    }

    private fun setupRecyclerView() {
        tracksAdapter = PlaylistTracksAdapter(tracks)
        binding.songsRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tracksAdapter
        }
    }

    private fun setupSortSpinner() {
        val sortOptions = resources.getStringArray(R.array.sort_options)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sortOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.sortSpinner.adapter = adapter

        binding.sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sortTracks(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setupButtons() {
        binding.btnPlay.setOnClickListener {
            // TODO: Implement play functionality
        }

        binding.btnShuffle.setOnClickListener {
            // TODO: Implement shuffle functionality
        }

        binding.btnDownload.setOnClickListener {
            // TODO: Implement download functionality
        }

        binding.btnShare.setOnClickListener {
            // TODO: Implement share functionality
        }

        binding.btnMore.setOnClickListener {
            // TODO: Implement more options
        }

        binding.fabAddSong.setOnClickListener {
            // TODO: Implement add song functionality
        }
    }

    private fun loadMockData() {
        // Mock data for demonstration
        tracks.addAll(listOf(
            Track(1, "Blinding Lights", "The Weeknd", "After Hours", "3:20", ""),
            Track(2, "Watermelon Sugar", "Harry Styles", "Fine Line", "2:54", ""),
            Track(3, "Levitating", "Dua Lipa", "Future Nostalgia", "3:23", ""),
            Track(4, "Good 4 U", "Olivia Rodrigo", "SOUR", "2:58", ""),
            Track(5, "Stay", "The Kid Laroi & Justin Bieber", "F*CK LOVE 3: OVER YOU", "2:21", "")
        ))
        tracksAdapter.notifyDataSetChanged()
        updatePlaylistInfo()
    }

    private fun updatePlaylistInfo() {
        binding.playlistName.text = "Mi Playlist Favorita"
        binding.playlistDescription.text = "Creada por ti • ${tracks.size} canciones"
    }

    private fun sortTracks(sortType: Int) {
        when (sortType) {
            0 -> {} // Custom order (no sort)
            1 -> tracks.sortBy { it.title } // Title
            2 -> tracks.sortBy { it.artist } // Artist
            3 -> {} // Date added (not implemented yet)
            4 -> {} // Duration (not implemented yet)
        }
        tracksAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}