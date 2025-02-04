package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PlaylistInfoViewModel(
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private var _playlistWithTracks = MutableLiveData<PlaylistWithTracksModel>()
    val playlistWithTracks: LiveData<PlaylistWithTracksModel>
        get() = _playlistWithTracks


    fun getPlaylistWithTracks(playlistId: Int) {

        viewModelScope.launch {
            val playlist: PlaylistWithTracksModel = withContext(Dispatchers.IO) {
                playlistInteractor.getPlaylistWithTracks(playlistId)
            }
            val currentPlaylist = playlist.copy(
                playlistId = playlist.playlistId,
                playListName = playlist.playListName,
                playListDescription = playlist.playListDescription,
                coverUri = playlist.coverUri,
                playlistTrackAmount = playlist.playlistTrackAmount,
                playlistTracks = playlist.playlistTracks
            )
            _playlistWithTracks.postValue(currentPlaylist)
        }

        /*viewModelScope.launch {
            val playlist = async {
                val playlist = playlistInteractor.getPlaylistWithTracks(playlistId)
                val currentPlaylist = playlist.copy(
                    playlistId = playlist.playlistId,
                    playListName = playlist.playListName,
                    playListDescription = playlist.playListDescription,
                    coverUri = playlist.coverUri,
                    playlistTrackAmount = playlist.playlistTrackAmount,
                    playlistTracks = playlist.playlistTracks
                )
                currentPlaylist
            }
            _playlistWithTracks.postValue(playlist.await())
        }*/

    }

}