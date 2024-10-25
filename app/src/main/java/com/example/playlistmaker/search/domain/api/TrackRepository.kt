package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.models.Tracks

interface TrackRepository {
    fun searchTracks(inputQuery: String): Tracks
    fun clearHistoryList()
    fun addTrackToList(track: Track)
    fun getHistoryList(): List<Track>
}