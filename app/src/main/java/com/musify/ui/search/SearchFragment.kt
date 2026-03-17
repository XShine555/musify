package com.musify.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.R
import com.musify.databinding.FragmentSearchBinding
import com.musify.model.SearchResultType
import com.musify.ui.common.UsageStatsRepository
import com.musify.ui.common.VerticalSpaceItemDecoration

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!
    private lateinit var usageStatsRepository: UsageStatsRepository
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        usageStatsRepository = UsageStatsRepository(requireContext())
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadPlaylists()
        val adapter = SearchResultAdapter(emptyList(), { item ->
            val bottomSheet = AddToPlaylistBottomSheet(
                playlists = viewModel.playlists.value ?: emptyList(),
                listener = object : OnAddToPlaylistListener {
                    override fun onAddToPlaylist(playlistId: Int) {

                        viewModel.addTrackToPlaylist(
                            trackId = item.id,
                            playlistId = playlistId
                        )
                        lifecycleScope.launch {
                            usageStatsRepository.incrementTrackAdds()
                        }
                        Toast.makeText(
                            requireContext(),
                            "Añadido a playlist",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )

            bottomSheet.show(parentFragmentManager, "add_to_playlist")
        })
        binding.searchList.adapter = adapter
        binding.searchList.layoutManager = LinearLayoutManager(requireContext())

        val spacing = resources.getDimensionPixelSize(R.dimen.item_margin_small)
        binding.searchList.addItemDecoration(
            VerticalSpaceItemDecoration(
                spacing
            )
        )

        viewModel.searchs.observe(viewLifecycleOwner) { searchResults ->
            adapter.updateList(searchResults)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) {
            val errorMessage = it ?: return@observe
            Toast.makeText(
                requireContext(), errorMessage, Toast.LENGTH_SHORT
            ).show()
        }

        // Actualiza la lista de resultados en función de los filtros activos y el texto de búsqueda.
        fun updateResults() {
            adapter.updateList(
                viewModel.searchs.value?.filter { searchResult ->
                    (searchResult.type != SearchResultType.USER || binding.usersChip.isChecked) && (searchResult.type != SearchResultType.TRACK || binding.tracksChip.isChecked) && searchResult.title.contains(
                        binding.searchInput.text.toString(), ignoreCase = true
                    )
                } ?: emptyList())
        }

        binding.tracksChip.setOnClickListener {
            updateResults()
        }
        binding.usersChip.setOnClickListener {
            updateResults()
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateResults()
            }
        })

        viewModel.loadAll()
    }
}
