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
import androidx.recyclerview.widget.LinearLayoutManager
import com.musify.databinding.FragmentSearchBinding
import com.musify.model.SearchResultType

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchResultAdapter(emptyList(), { item ->
            Toast.makeText(
                requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
            ).show()
        })
        binding.searchList.adapter = adapter
        binding.searchList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.searchs.observe(viewLifecycleOwner) { searchResults ->
            adapter.updateList(searchResults)
        }

        // Actualiza la lista de resultados en función de los filtros activos y el texto de búsqueda.
        fun updateResults() {
            adapter.updateList(
                SearchDataSource.items.filter { searchResult ->
                    (searchResult.type != SearchResultType.USER || binding.checkBoxUser.isChecked) && (searchResult.type != SearchResultType.TRACK || binding.checkBoxTracks.isChecked) && searchResult.title.contains(
                        binding.searchInput.text.toString(), ignoreCase = true
                    )
                })
        }

        binding.checkBoxTracks.setOnClickListener {
            updateResults()
        }
        binding.checkBoxUser.setOnClickListener {
            updateResults()
        }

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateResults()
            }
        })
    }
}
