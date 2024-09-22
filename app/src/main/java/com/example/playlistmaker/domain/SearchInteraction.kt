package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.models.Track

interface SearchInteraction {
    fun searchTrack(
        query: String,
        isRefreshed: Boolean = false,
        resultLambda: (SearchResult) -> Unit,
    )

    fun clearTrackHistory()

    fun addTrackToHistory(track: Track)
    fun restoreHistoryCache() : List<Track>
}