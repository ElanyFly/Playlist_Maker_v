package com.example.playlistmaker.media.data.temporary

import com.example.playlistmaker.search.domain.models.Track

data class PlaylistModel (
    val playlistId: Int,
    val playListName: String,
    val playListDescription: String,
    val coverUri: String,
    val playlistTracks: List<Track>,
    val playlistTrackAmount: Int,
)