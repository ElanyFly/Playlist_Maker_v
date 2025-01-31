package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlaylistFragmentViewModel(
    private val playlistInteractor: PlaylistInteractor
): ViewModel() {

    private val _state = MutableLiveData<PlaylistWithTracksEntity>()
    val state: LiveData<PlaylistWithTracksEntity>
        get() = _state

    fun getAllPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.getAllPlaylists()
                .collect { playlists ->

                }
        }

    }

}