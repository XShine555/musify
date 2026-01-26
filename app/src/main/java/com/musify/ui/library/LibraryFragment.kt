package com.musify.ui.library

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.databinding.FragmentLibraryBinding
import com.musify.ui.common.PlaylistDataSource

class LibraryFragment : Fragment() {
    private var _binding: FragmentLibraryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LibraryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLibraryBinding.inflate(inflater, container, false)

        val adapter = PlaylistResultAdapter(
            emptyList(), { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        binding.playlistsList.adapter = adapter
        binding.playlistsList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.playlists.observe(viewLifecycleOwner) { playlistResults ->
            adapter.updateList(playlistResults)
        }

        val searchInput = binding.searchInput
        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                adapter.updateList(
                    PlaylistDataSource.items.filter { item ->
                        item.title.contains(s.toString(), true) || item.owner.contains(
                            s.toString(), true
                        )
                    })
            }

            override fun beforeTextChanged(
                s: CharSequence?, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?, start: Int, before: Int, count: Int
            ) {
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
