package com.musify.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.musify.model.SearchResult
import kotlin.collections.emptyList

class SearchViewModel: ViewModel() {
    private val _searchs = MutableLiveData(SearchDataSource.items)
    val searchs: LiveData<List<SearchResult>> = _searchs

    fun addSearchResult(searchResult: SearchResult) {
        val currentList = _searchs.value ?: emptyList()
        _searchs.value = currentList + searchResult
    }

    fun removeSearchResult(searchResult: SearchResult) {
        val currentList = _searchs.value ?: emptyList()
        _searchs.value = currentList - searchResult
    }
}