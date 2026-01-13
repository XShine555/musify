package com.musify.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musify.R
import com.musify.model.SearchResultType

class SearchFragment : Fragment() {
    private lateinit var adapter: SearchResultAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.searchRecyclerView)

        adapter = SearchResultAdapter(SearchDataSource.items, { item ->
            Toast.makeText(
                requireContext(), "Has clicat: ${item.title}", Toast.LENGTH_SHORT
            ).show()
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val btnSongs: CheckBox = view.findViewById(R.id.checkBoxSongs)
        val btnUsers: CheckBox = view.findViewById(R.id.checkBoxUsers)
        val searchEditText: EditText = view.findViewById(R.id.searchEditText)

        // Actualiza la lista de resultados en función de los filtros activos y el texto de búsqueda.
        fun updateResults() {
            adapter.updateList(
                SearchDataSource.items.filter { searchResult ->
                    (searchResult.type != SearchResultType.USER || btnUsers.isChecked) && (searchResult.type != SearchResultType.TRACK || btnSongs.isChecked) && searchResult.title.contains(
                        searchEditText.text.toString(), ignoreCase = true
                    )
                })
        }

        btnSongs.setOnClickListener {
            updateResults()
        }
        btnUsers.setOnClickListener {
            updateResults()
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateResults()
            }
        })
    }
}
