package com.example.playlistmaker.media.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.data.temporary.PlaylistInteractor
import com.example.playlistmaker.media.data.temporary.PlaylistModel
import kotlinx.coroutines.launch

class CreatePlaylistViewModel(
    private val playlistInteractor: PlaylistInteractor
): ViewModel() {


    fun makeAction(action: CreatePlaylistAction) {
        when(action) {
            is CreatePlaylistAction.AddNewPlaylistToDb -> handleAddNewPlaylistToDb(action)
            CreatePlaylistAction.LoadImage -> handleLoadImage()
        }
    }

    private fun handleLoadImage() {

    }

    private fun handleAddNewPlaylistToDb(action: CreatePlaylistAction.AddNewPlaylistToDb) {

    }

    fun createPlaylist(playlist: PlaylistModel) {
        viewModelScope.launch {
            playlistInteractor.createNewPlaylist(
                playlist = playlist
            )
        }
    }
    fun getPlaylists(){
//        playlistInteractor.getAllPlaylists()
    }
}