package com.example.playlistmaker.ui.search

import com.example.playlistmaker.domain.models.Track

sealed interface SearchAction {
    class SearchTrack(
        val inputQuery: String,
        val isRefreshed: Boolean
    ) : SearchAction

    data object ClearTrackHistory : SearchAction
    data object RestoreHistoryCache : SearchAction
    class AddTrackToHistoryList(val track: Track) : SearchAction
}
