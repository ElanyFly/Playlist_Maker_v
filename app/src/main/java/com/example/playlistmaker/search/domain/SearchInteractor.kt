package com.example.playlistmaker.search.domain

import com.example.playlistmaker.search.domain.models.Track

interface SearchInteractor {
    fun searchTrack(
        query: String,
        isRefreshed: Boolean = false,
        resultLambda: (SearchResult) -> Unit,
    )

    fun clearTrackHistory()
    fun addTrackToHistory(track: Track)
    fun restoreHistoryCache() : List<Track>
}