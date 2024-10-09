package com.example.playlistmaker.search.domain.api

import com.example.playlistmaker.search.domain.models.Tracks

interface TrackRepository {
    fun searchTracks(inputQuery: String): Tracks
}