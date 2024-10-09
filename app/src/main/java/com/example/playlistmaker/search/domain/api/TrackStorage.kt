package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.Track

interface TrackStorage {
    fun clearHistoryList()
    fun addTrackToList(track: Track)
    fun getHistoryList(): List<Track>
}