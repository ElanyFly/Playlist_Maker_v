package com.example.playlistmaker.search.domain.models

data class Tracks(
    val isError: Boolean = false,
    val trackList: List<Track> = emptyList()
)