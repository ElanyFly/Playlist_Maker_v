package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.models.Tracks
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    suspend fun searchTracks(inputQuery: String): Flow<Tracks>?
    suspend fun clearHistoryList()
    suspend fun addTrackToList(track: Track)
    suspend fun getHistoryList(): List<Track>
}