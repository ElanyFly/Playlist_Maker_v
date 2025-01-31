package com.example.playlistmaker.media.presentation.playlist

sealed interface CreatePlaylistAction {
    data object LoadImage : CreatePlaylistAction
    class AddNewPlaylistToDb(val inputQuery: String) : CreatePlaylistAction
}