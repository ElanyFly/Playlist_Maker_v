package com.example.playlistmaker.search.ui

import com.example.playlistmaker.search.domain.models.Track

sealed interface SearchAction {
    class SearchTrack(
        val inputQuery: String,
        val isRefreshed: Boolean
    ) : SearchAction

    data object ClearTrackHistory : SearchAction
    data object RestoreHistoryCache : SearchAction
    data object ClearSearchQuery : SearchAction

    class AddTrackToHistoryList(val track: Track) : SearchAction
}
