package com.example.playlistmaker.media.presentation.favourite_tracks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.launch

class FavoriteTracksFragmentViewModel(
    private val tracksInteractor: TracksInteractor
) : ViewModel() {

    private val _state = MutableLiveData<FavoriteTracksFragmentState>()
    val state: LiveData<FavoriteTracksFragmentState>
        get() = _state

    fun getContent() {
        viewModelScope.launch {
            tracksInteractor
                .getFavTracksList()
                .collect { tracks ->
                    processResult(tracks)
                }
        }
    }

    private fun processResult(tracks: List<Track>) {
        if (tracks.isEmpty()) {
            renderState(FavoriteTracksFragmentState.Empty)
        } else {
            renderState(FavoriteTracksFragmentState.Content(tracks))
        }
    }

    private fun renderState(state: FavoriteTracksFragmentState) {
        _state.postValue(state)
    }

}