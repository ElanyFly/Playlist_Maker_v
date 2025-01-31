package com.example.playlistmaker.media.domain.db.model

data class PlaylistModel (
    val playlistId: Int,
    val playListName: String,
    val playListDescription: String,
    val coverUri: String,
//    val playlistTracks: List<Track>,
    val playlistTrackAmount: Int,
)