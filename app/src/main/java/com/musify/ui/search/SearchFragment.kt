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
import com.musify.model.SearchItem
import com.musify.model.SearchType

class SearchFragment : Fragment() {

    private lateinit var adapter: SearchAdapter
    private lateinit var recyclerView: RecyclerView

    // Lista completa de ejemplo
    private val allResults = listOf(
        SearchItem(
            "Moscow Mule",
            "https://i.scdn.co/image/ab67616d0000b27349d694203245f241a1bcaa72",
            SearchType.TRACK
        ), SearchItem(
            "Como Antes",
            "https://i.scdn.co/image/ab67616d0000b273519266cd05491a5b5bc22d1e",
            SearchType.TRACK
        ), SearchItem("Dua Lipa", "https://picsum.photos/202", SearchType.USER), SearchItem(
            "Quevedo",
            "https://akamai.sscdn.co/uploadfile/letras/fotos/1/c/4/1/1c41718dc8bd31b7bfdc49e4d1d10be8.jpg",
            SearchType.USER
        ), SearchItem(
            "La Última",
            "https://cdn-images.dzcdn.net/images/artist/79880cc1b999b15567e332203464c34e/1900x1900-000000-81-0-0.jpg",
            SearchType.TRACK
        )
    )

    private var filterSongs = false
    private var filterUsers = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

        fun applyFilter() {
            val query = searchEditText.text.toString().lowercase()
            val filtered = allResults.filter {
                val matchesQuery = it.name.lowercase().contains(query)
                val matchesFilter =
                    (!filterSongs && !filterUsers) || (filterSongs && it.type == SearchType.TRACK) || (filterUsers && it.type == SearchType.USER)
                matchesQuery && matchesFilter
            }
            adapter.updateList(filtered)
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
}
