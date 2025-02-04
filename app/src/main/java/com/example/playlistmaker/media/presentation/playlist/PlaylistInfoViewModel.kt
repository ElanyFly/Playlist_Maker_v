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

    private var currentPlaylist: PlaylistWithTracksModel? = null
    private var playlistJob: Job? = null


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

//        var playlistInfo: PlaylistWithTracksModel? =
//            viewModelScope.async {
//                val playlistThis = playlistInteractor.getPlaylistWithTracks(playlistId)
//                _playlistWithTracks.value?.copy(
//                    playlistId = playlistThis.playlistId,
//                    playListName = playlistThis.playListName,
//                    playListDescription = playlistThis.playListDescription,
//                    coverUri = playlistThis.coverUri,
//                    playlistTrackAmount = playlistThis.playlistTrackAmount,
//                    playlistTracks = playlistThis.playlistTracks
//                )
//            }.await()
//        return playlistInfo!!

    }


//        return PlaylistWithTracksModel(
//            playlistId = 0,
//            playListName = "sdf",
//            playListDescription = "wer",
//            coverUri = "",
//            playlistTrackAmount = 2,
//            playlistTracks = emptyList()
//
//        )




}