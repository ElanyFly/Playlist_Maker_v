package com.example.playlistmaker.media.presentation.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.sharing.domain.SharingInteractor
import kotlinx.coroutines.launch

class PlaylistInfoViewModel(
    private val playlistInteractor: PlaylistInteractor,
    private val tracksInteractor: TracksInteractor,
    private val intentNavigation: SharingInteractor
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
            val isElsewhere = playlistInteractor.isTrackInAnyPlaylist(trackId = track.trackId)
            val isInFav = tracksInteractor.isTrackExistsInFav(trackId = track.trackId)
            if (!isElsewhere && !isInFav) {
                tracksInteractor.deleteTrackById(trackId = track.trackId)
            }
        }
    }

    fun getCurrentPlaylist(): PlaylistWithTracksModel? = playlistWithTracks.value

    fun sharePlaylist() {
        intentNavigation.sharePlaylist(
            playlistWithTracks.value ?: return
        )
    }

}