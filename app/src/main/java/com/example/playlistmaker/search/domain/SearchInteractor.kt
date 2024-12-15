package com.example.playlistmaker.search.domain

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.models.Tracks
import kotlinx.coroutines.flow.Flow

interface SearchInteractor {
    suspend fun searchTrack(
        query: String,
        isRefreshed: Boolean = false,
    ): Flow<SearchResult>?

    suspend fun clearTrackHistory()
    suspend fun addTrackToHistory(track: Track)
    suspend fun restoreHistoryCache() : List<Track>
}