package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.models.Track

sealed interface SearchResult {
    data class Success(val trackList: List<Track>): SearchResult
    data class Error(
        val isNothingFound: Boolean = false,
        val isNetworkError: Boolean = false
    ): SearchResult

    data object Loading: SearchResult
}