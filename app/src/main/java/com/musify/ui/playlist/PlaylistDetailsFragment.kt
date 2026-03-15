package com.musify.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.musify.R
import com.musify.databinding.FragmentPlaylistDetailsBinding
import com.musify.model.TrackSortField
import com.musify.ui.common.VerticalSpaceItemDecoration

class PlaylistDetailsFragment : Fragment() {

    private var _binding: FragmentPlaylistDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PlaylistDetailsViewModel by viewModels()

    private val args: PlaylistDetailsFragmentArgs by navArgs()

    private var trackSortField = TrackSortField.TITLE
    private var isSortAscending = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistDetailsBinding.inflate(inflater, container, false)

        val tracksAdapter = PlaylistTracksAdapter()
        binding.tracksRecyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = tracksAdapter
        }
        val tacksSpacing =
            resources.getDimensionPixelSize(R.dimen.item_margin_medium)
        binding.tracksRecyclerView.addItemDecoration(
            VerticalSpaceItemDecoration(
                tacksSpacing
            )
        )
        binding.tracksRecyclerView.adapter = tracksAdapter

        viewModel.playlistDetails.observe(viewLifecycleOwner) { playlistDetails ->
            val playlistImage = binding.playlistImage
            val context = requireContext()
            val radius = context.resources.getDimensionPixelSize(R.dimen.radius_small)
            Glide.with(context).load(playlistDetails.imageUrl).centerCrop()
                .placeholder(R.drawable.img_playlist_placeholder).transform(RoundedCorners(radius))
                .into(playlistImage)

            binding.playlistTitle.text = playlistDetails.title
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            val errorMessage = errorMessage ?: return@observe
            Toast.makeText(
                requireContext(), errorMessage, Toast.LENGTH_SHORT
            ).show()
        }

        binding.goBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val sharedPreferences = requireContext().getSharedPreferences("auth_preferences", 0)
        val accessToken = sharedPreferences.getString("access_token", "") ?: ""
        binding.editButton.setOnClickListener {
            val fragment = EditPlaylistBottomSheetFragment(
                {
                    viewModel.loadPlaylistDetails(args.playlistId, accessToken)
                })
            val argsBundle = Bundle()
            argsBundle.putInt("playlistId", viewModel.playlistDetails.value?.id ?: 0)
            argsBundle.putString("playlistName", viewModel.playlistDetails.value?.title)
            argsBundle.putString("playlistImageUrl", viewModel.playlistDetails.value?.imageUrl)
            fragment.arguments = argsBundle
            fragment.show(parentFragmentManager, "edit_playlist_sheet")
        }
        viewModel.loadPlaylistDetails(args.playlistId, accessToken)

        binding.sortButton.setOnClickListener {
            val sortSheet = SortTracksBottomSheetFragment { selectedSortField ->
                if (trackSortField == selectedSortField) {
                    isSortAscending = !isSortAscending
                } else {
                    isSortAscending = true
                }
                trackSortField = selectedSortField
                viewModel.loadTracks(
                    args.playlistId, accessToken, trackSortField, isSortAscending
                )
            }
            val argsBundle = Bundle()
            argsBundle.putInt("sortField", trackSortField.ordinal)
            argsBundle.putBoolean("ascending", isSortAscending)
            sortSheet.arguments = argsBundle
            sortSheet.show(parentFragmentManager, "sort_tracks_sheet")
        }

        viewModel.tracks.observe(viewLifecycleOwner) { tracks ->
            tracksAdapter.updateList(tracks)
            binding.playlistSummary.text =
                "Creada Por Ti · Contiene ${tracks.size} Canciones."
        }
        viewModel.loadTracks(
            args.playlistId, accessToken, trackSortField, isSortAscending
        )

        binding.deleteButton.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Confirmación")
                .setMessage("¿Estás seguro de que quieres eliminar la Playlist?")
                .setPositiveButton("Sí") { dialog, _ ->
                    viewModel.deletePlaylist(
                        args.playlistId,
                        accessToken
                    )
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
        viewModel.deletePlaylistSuccess.observe(viewLifecycleOwner) { isDeleted ->
            if (isDeleted) {
                Toast.makeText(
                    requireContext(), "Playlist eliminada exitosamente", Toast.LENGTH_SHORT
                ).show()
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}