package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.stateIn
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
                playlistInteractor.getPlaylistWithTracks(playlistId).collect {
                    _playlistWithTracks.postValue(it)
                }
        }
    }

    fun deleteTrackFromPlaylist(track: Track) {
        viewModelScope.launch {
            val connection =
                playlistWithTracks.value?.let { PlaylistTrackJoinModel(it.playlistId, track.trackId) } ?: return@launch
            playlistInteractor.deleteConnection(connection)
        }
    }

}