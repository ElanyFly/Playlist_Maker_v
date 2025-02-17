package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    private val playlistInteractor: PlaylistInteractor
): ViewModel() {

    fun createPlaylist(playListName: String, playListDescription: String, coverUri: String, playlistId: Int = 0, playlistTrackAmount: Int = 0) {
        viewModelScope.launch {
            val newPlaylist = PlaylistModel(
                playlistId = playlistId,
                playListName = playListName,
                playListDescription = playListDescription,
                coverUri = coverUri,
                playlistTrackAmount = playlistTrackAmount
            )
            playlistInteractor.createNewPlaylist(
                playlist = newPlaylist
            )
        }
    }

}