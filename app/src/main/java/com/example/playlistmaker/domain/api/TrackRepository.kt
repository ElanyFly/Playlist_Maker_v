package com.example.playlistmaker.domain.api

import com.example.playlistmaker.domain.models.Tracks

interface TrackRepository {
    fun searchTracks(inputQuery: String): Tracks
}