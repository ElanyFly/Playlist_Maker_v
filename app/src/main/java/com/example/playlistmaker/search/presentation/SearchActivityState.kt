package com.example.playlistmaker.search.presentation

import com.example.playlistmaker.search.domain.models.Track

data class SearchActivityState(
    val trackList: List<Track>,
    val isNothingFound: Boolean,
    val isNetworkError: Boolean,
    val isLoading: Boolean,
    val isHistoryShown: Boolean
) {
    companion object {
        val defaultState = SearchActivityState(
            trackList = emptyList(),
            isNothingFound = false,
            isNetworkError = false,
            isLoading = false,
            isHistoryShown = false
        )
    }
}