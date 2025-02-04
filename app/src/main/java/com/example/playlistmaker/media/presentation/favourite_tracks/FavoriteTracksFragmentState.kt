package com.example.playlistmaker.media.presentation.favourite_tracks

import com.example.playlistmaker.search.domain.models.Track

sealed interface FavoriteTracksFragmentState {

    data object Empty: FavoriteTracksFragmentState

    data class Content(val tracks: List<Track>) : FavoriteTracksFragmentState
}