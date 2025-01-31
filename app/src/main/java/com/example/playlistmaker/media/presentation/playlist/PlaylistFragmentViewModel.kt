package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlaylistFragmentViewModel(
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<PlaylistState>(PlaylistState.Empty)
    val state: StateFlow<PlaylistState>
        get() = _state.asStateFlow()

    fun getAllPlaylists() {
        viewModelScope.launch(Dispatchers.IO) {
            playlistInteractor.getAllPlaylists().collect { playlists ->
                val newState = if (playlists.isNotEmpty()) {
                    PlaylistState.ShowContent(playlists = playlists)
                } else {
                    PlaylistState.Empty
                }
                _state.update { newState }
            }
        }
    }


}