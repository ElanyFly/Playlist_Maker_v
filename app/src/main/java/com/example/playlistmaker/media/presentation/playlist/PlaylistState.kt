package com.example.playlistmaker.media.presentation.playlist

import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel

sealed interface PlaylistState {

    data object Empty: PlaylistState

    data class ShowContent(val playlists: List<PlaylistWithTracksModel>) : PlaylistState
}