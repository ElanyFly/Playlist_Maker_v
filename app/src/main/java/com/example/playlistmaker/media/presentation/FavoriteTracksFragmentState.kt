package com.example.playlistmaker.media.presentation

import com.example.playlistmaker.search.domain.models.Track

sealed interface FavoriteTracksFragmentState {

    object Empty: FavoriteTracksFragmentState

    data class Content(val tracks: List<Track>) : FavoriteTracksFragmentState
}