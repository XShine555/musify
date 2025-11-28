package com.musify.ui.library

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.databinding.FragmentLibraryBinding
import com.musify.model.Playlist
import com.musify.ui.create_playlist_modal.CreatePlaylistModalFragment
import com.musify.ui.create_playlist_modal.OnPlaylistCreatedListener

class LibraryFragment : Fragment() {

    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LibraryViewModel by viewModels()

    private lateinit var adapter: PlaylistAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        adapter = PlaylistAdapter()
        binding.recyclerViewHome.adapter = adapter
        binding.recyclerViewHome.layoutManager = LinearLayoutManager(requireContext())
        
        viewModel.playlists.observe(viewLifecycleOwner) { playlists ->
            adapter.submitList(playlists)
        }

        val searchInput = binding.searchInput
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.searchPlaylists(s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
            }
        })

        val addPlaylistButton = binding.addPlaylistButton
        addPlaylistButton.setOnClickListener {
            val playlistModal = CreatePlaylistModalFragment()
            playlistModal.show(parentFragmentManager, "CreatePlaylistModal")

            playlistModal.listener = object: OnPlaylistCreatedListener {
                override fun onPlaylistCreated(playlistName: String) {
                    viewModel.addPlaylist(Playlist(-1, playlistName, "User", ""))
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
