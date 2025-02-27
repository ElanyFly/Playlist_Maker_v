package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    private val playlistInteractor: PlaylistInteractor
): ViewModel() {

    private lateinit var playlistToChange: PlaylistModel

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

    fun updatePlaylist(playlistToEdit: PlaylistWithTracksModel, playListName: String, playListDescription: String, coverUri: String) {

        viewModelScope.launch(Dispatchers.IO) {

            playlistToChange = playlistInteractor.getPlaylistById(playlistToEdit.playlistId)

            val updatedPlaylist = playlistToChange.copy(
                playListName = playListName,
                playListDescription = playListDescription,
                coverUri = coverUri,
                )

            playlistInteractor.updatePlaylist(updatedPlaylist)
        }
    }

}