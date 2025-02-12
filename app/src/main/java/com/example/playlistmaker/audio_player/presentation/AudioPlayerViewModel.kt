package com.example.playlistmaker.audio_player.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.audio_player.domain.PlayerControl
import com.example.playlistmaker.audio_player.domain.StatePlayer
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AudioPlayerViewModel(
    private val mediaPlayer: PlayerControl,
    private val tracksInteractor: TracksInteractor,
    private val playlistInteractor: PlaylistInteractor
) : ViewModel() {

    private val _playerState = MutableLiveData<AudioPlayerState>(AudioPlayerState.defaultState)
    val playerState: LiveData<AudioPlayerState>
        get() = _playerState

    private var playerJob: Job? = null

    private var currentTrack: Track? = null

    init {
        mediaPlayer.timeFlow.observeForever { time ->
            handleState(time = time)
        }
        mediaPlayer.stateFlow.observeForever { statePlayer ->
            handleState(state = statePlayer)
        }

    }

    fun makeAction(action: AudioPlayerAction) {
        when (action) {
            is AudioPlayerAction.prepareTrack -> handlePrepareTrack(action)
            is AudioPlayerAction.pressPlayBtn -> handlePressPlayBtn(action)
            is AudioPlayerAction.pressLikeBtn -> handlePressLikeBtn(action)
        }
    }

    private fun handlePressLikeBtn(action: AudioPlayerAction.pressLikeBtn) {
        val currentTrackM = currentTrack ?: return
        viewModelScope.launch {
            val isFavourite = !currentTrackM.isFavorite
            val currentTrackNew = currentTrackM.copy(
                isFavorite = isFavourite
            )
            val isInFav = tracksInteractor.isTrackExistsInFav(currentTrackNew.trackId)
            val isElsewhere = playlistInteractor.isTrackInAnyPlaylist(trackId = currentTrackNew.trackId)

            if (!isFavourite){
                tracksInteractor.updateFavouriteStatus(currentTrackNew.trackId, false)
                if (!isElsewhere) {
                    tracksInteractor.deleteTrackById(trackId = currentTrackNew.trackId)
                }
            } else {
                tracksInteractor.updateFavouriteStatus(currentTrackNew.trackId, true)
                if (!isInFav){
                    tracksInteractor.addTrack(currentTrackNew)
                }
            }
            currentTrack = currentTrackNew
            handleState(track = currentTrackNew)

        }

    }

    private fun handlePressPlayBtn(action: AudioPlayerAction.pressPlayBtn) {
        playerJob?.cancel()
        playerJob = viewModelScope.launch {
            mediaPlayer.playbackControl(action.isStopped)
        }
    }

    private fun handlePrepareTrack(action: AudioPlayerAction.prepareTrack) {

        viewModelScope.launch {
            val isFavourite: Boolean = tracksInteractor.getFavStatus(action.track.trackId)
            val newCurrentTrack = action.track.copy(isFavorite = isFavourite)
            mediaPlayer.preparePlayer(newCurrentTrack)
            currentTrack = newCurrentTrack
            handleState(track = currentTrack)
        }
    }

    private fun handleState(
        track: Track? = currentTrack,
        time: String = mediaPlayer.timeFlow.value.toString(),
        state: StatePlayer = mediaPlayer.stateFlow.value ?: StatePlayer.PREPARED
    ) {
        val newValue = _playerState.value?.let {
            it.copy(
                track = track ?: it.track,
                playTime = time,
                isPlaying = state == StatePlayer.PLAYING,
                isPaused = state == StatePlayer.PAUSED,
                isFinished = state == StatePlayer.PREPARED

            )
        } ?: return

        _playerState.postValue(newValue)

    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.releasePlayer()
    }

}