package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Track

interface TrackStorage {
    fun clearHistoryList()
    fun addTrackToList(track: Track)
    fun getHistoryList(): List<Track>
}