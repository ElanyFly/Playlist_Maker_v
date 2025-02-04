package com.example.playlistmaker.media.domain.db.model

import com.example.playlistmaker.search.domain.models.Track

data class PlaylistWithTracksModel (
    val playlistId: Int,
    val playListName: String,
    val playListDescription: String,
    val coverUri: String,
    val playlistTrackAmount: Int,
    val playlistTracks: List<Track>,
)