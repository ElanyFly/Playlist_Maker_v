package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.models.Tracks
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun searchTracks(inputQuery: String): Flow<Tracks>?
    fun clearHistoryList()
    fun addTrackToList(track: Track)
    fun getHistoryList(): List<Track>
}