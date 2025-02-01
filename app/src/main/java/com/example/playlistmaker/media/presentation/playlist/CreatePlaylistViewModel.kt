package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    private val playlistInteractor: PlaylistInteractor
): ViewModel() {

    fun createPlaylist(playListName: String, playListDescription: String, coverUri: String) {
        viewModelScope.launch {
            val newPlaylist = PlaylistModel(
                playlistId = 0,
                playListName = playListName,
                playListDescription = playListDescription,
                coverUri = coverUri,
                playlistTrackAmount = 0
            )
            playlistInteractor.createNewPlaylist(
                playlist = newPlaylist
            )
        }
    }

}