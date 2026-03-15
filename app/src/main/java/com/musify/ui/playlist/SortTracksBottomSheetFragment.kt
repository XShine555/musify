package com.musify.ui.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.musify.R
import com.musify.model.TrackSortField

class SortTracksBottomSheetFragment(
    private val onSortOptionSelected: (TrackSortField) -> Unit
) : BottomSheetDialogFragment() {
    var selectedOption = TrackSortField.TITLE
    var ascending = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        selectedOption = arguments?.getInt("sortField")?.let { TrackSortField.entries[it] }
            ?: TrackSortField.TITLE
        ascending = arguments?.getBoolean("ascending") ?: false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.bottom_sheet_sort_tracks_layout, container, false)

        val titleSortOption = view.findViewById<MaterialButton>(R.id.sort_by_title_button)
        val artistSortOption = view.findViewById<MaterialButton>(R.id.sort_by_artist_button)
        val durationSortOption = view.findViewById<MaterialButton>(R.id.sort_by_duration_button)
        val dateAddedSortOption = view.findViewById<MaterialButton>(R.id.sort_by_date_added_button)

        val arrowDrawable = if (ascending) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_north)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_south)
        }

        listOf(titleSortOption, artistSortOption, durationSortOption, dateAddedSortOption).forEach {
            it.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }

        when (selectedOption) {
            TrackSortField.TITLE -> titleSortOption.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDrawable, null)
            TrackSortField.ARTIST -> artistSortOption.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDrawable, null)
            TrackSortField.DURATION -> durationSortOption.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDrawable, null)
            TrackSortField.DATE_ADDED -> dateAddedSortOption.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDrawable, null)
        }

        titleSortOption.setOnClickListener {
            onSortOptionSelected(TrackSortField.TITLE)
            dismiss()
        }

        artistSortOption.setOnClickListener {
            onSortOptionSelected(TrackSortField.ARTIST)
            dismiss()
        }

        durationSortOption.setOnClickListener {
            onSortOptionSelected(TrackSortField.DURATION)
            dismiss()
        }

        dateAddedSortOption.setOnClickListener {
            onSortOptionSelected(TrackSortField.DATE_ADDED)
            dismiss()
        }

        return view
    }
}