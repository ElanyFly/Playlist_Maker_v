package com.example.playlistmaker.media.presentation.playlist

import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity

sealed interface PlaylistState {

    data object Empty: PlaylistState

    data class ShowContent(val playlists: List<PlaylistWithTracksEntity>) : PlaylistState
}