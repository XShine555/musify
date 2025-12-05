package com.musify.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.musify.R

class SearchFragment : Fragment() {

    private lateinit var adapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView

    // Lista completa de ejemplo
    private val allResults = listOf(
        SearchItem("Song 1", "https://picsum.photos/200", "song"),
        SearchItem("Song 2", "https://picsum.photos/201", "song"),
        SearchItem("Dua Lipa", "https://picsum.photos/202", "user"),
        SearchItem("User123", "https://picsum.photos/203", "user"),
        SearchItem("Song 3", "https://cdn-images.dzcdn.net/images/artist/79880cc1b999b15567e332203464c34e/1900x1900-000000-81-0-0.jpg", "song"),
        SearchItem("UserX", "https://picsum.photos/205", "user")
    )

    private var filterSongs = false
    private var filterUsers = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.searchRecyclerView)
        adapter = SearchAdapter(mutableListOf())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val btnSongs = view.findViewById<MaterialButton>(R.id.btnSongs)
        val btnUsers = view.findViewById<MaterialButton>(R.id.btnUsers)
        val searchEditText = view.findViewById<android.widget.EditText>(R.id.searchEditText)

        // Mostrar todos los resultados por defecto
        adapter.updateList(allResults)
        updateButtonColors(btnSongs, btnUsers)

        fun applyFilter() {
            val query = searchEditText.text.toString().lowercase()
            val filtered = allResults.filter {
                val matchesQuery = it.name.lowercase().contains(query)
                val matchesFilter = (!filterSongs && !filterUsers) ||  // Si ningún filtro activo, mostrar todo
                        (filterSongs && it.type == "song") ||
                        (filterUsers && it.type == "user")
                matchesQuery && matchesFilter
            }
            adapter.updateList(filtered)
            updateButtonColors(btnSongs, btnUsers)
        }

        btnSongs.setOnClickListener {
            filterSongs = !filterSongs
            applyFilter()
        }

        btnUsers.setOnClickListener {
            filterUsers = !filterUsers
            applyFilter()
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                applyFilter()
            }
        })
    }

    private fun updateButtonColors(btnSongs: MaterialButton, btnUsers: MaterialButton) {
        btnSongs.setBackgroundColor(
            if (filterSongs) resources.getColor(R.color.secondary) else resources.getColor(R.color.dark_gray)
        )
        btnUsers.setBackgroundColor(
            if (filterUsers) resources.getColor(R.color.secondary) else resources.getColor(R.color.dark_gray)
        )
    }
}
