package com.musify.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.databinding.FragmentUserBinding
import com.musify.ui.common.HorizontalSpaceItemDecoration

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
        binding.playlistList.adapter = playlistsAdapter
        binding.playlistList.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val playlistSpacing = resources.getDimensionPixelSize(R.dimen.item_margin_medium)
        binding.playlistList.addItemDecoration(HorizontalSpaceItemDecoration(playlistSpacing))

        viewModel.playlists.observe(viewLifecycleOwner) { playlistResults ->
            playlistsAdapter.updateList(playlistResults)
        }

        val tracksAdapter = UserTracksAdapter(
            emptyList(), { item ->
                Toast.makeText(
                    requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
                ).show()
            })
        binding.trackLists.adapter = tracksAdapter
        binding.trackLists.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false
        )
        val trackSpacing = resources.getDimensionPixelSize(R.dimen.item_margin_medium)
        binding.trackLists.addItemDecoration(HorizontalSpaceItemDecoration(trackSpacing))

        viewModel.tracks.observe(viewLifecycleOwner) { trackResults ->
            tracksAdapter.updateList(trackResults)
        }

        val userImage = "https://cdn.pfps.gg/pfps/1957-patrick-star-profile-photo.png"
        Glide.with(requireContext()).load(userImage).centerCrop()
            .placeholder(R.drawable.ic_person)
            .transform(RoundedCorners(R.dimen.radius_medium)).into(binding.userIcon)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}