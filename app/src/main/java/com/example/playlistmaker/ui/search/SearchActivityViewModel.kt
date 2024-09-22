package com.example.playlistmaker.ui.search

import androidx.lifecycle.ViewModel
import com.example.playlistmaker.Creator
import com.example.playlistmaker.domain.SearchResult
import com.example.playlistmaker.domain.models.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchActivityViewModel: ViewModel() {

    private val _state = MutableStateFlow<SearchActivityState>(SearchActivityState.defaultState)
    val state = _state.asStateFlow()
    fun makeAction(action: SearchAction) {
        when(action) {
            is SearchAction.AddTrackToHistoryList -> handleAddTrackToHistory(action)
            is SearchAction.ClearTrackHistory -> handleClearTrackHistory()
            is SearchAction.SearchTrack -> handleSearchTrack(action)
            is SearchAction.RestoreHistoryCache -> handleRestoreHistoryCache()
            is SearchAction.ClearSearchQuery -> handleRestoreHistoryCache()
        }
    }

    private fun handleRestoreHistoryCache() {
        val historyList = Creator.searchInteraction.restoreHistoryCache()
        if (historyList.isEmpty()) {
            return
        }
        handleState(
            trackList = historyList,
            isNothingFound = false,
            isNetworkError = false,
            isLoading = false
        )
    }

    private fun handleSearchTrack(action: SearchAction.SearchTrack) {
        Creator.searchInteraction.searchTrack(
            query = action.inputQuery,
            isRefreshed = action.isRefreshed,
            resultLambda = { searchResult ->
                when(searchResult){
                    is SearchResult.Error -> handleState(
                        isLoading = false,
                        isNothingFound = searchResult.isNothingFound,
                        isNetworkError = searchResult.isNetworkError,
                        trackList = emptyList()
                    )
                    is SearchResult.Success -> handleState(
                        isLoading = false,
                        trackList = searchResult.trackList,
                        isNetworkError = false,
                        isNothingFound = false
                    )
                    is SearchResult.Loading -> handleState(
                        isLoading = true,
                        isNetworkError = false,
                        isNothingFound = false,
                        trackList = emptyList()
                    )
                }
            }
        )
    }

    private fun handleClearTrackHistory() {
        Creator.searchInteraction.clearTrackHistory()
    }

    private fun handleAddTrackToHistory(action: SearchAction.AddTrackToHistoryList) {
        Creator.searchInteraction.addTrackToHistory(action.track)
    }

    @Synchronized
    private fun handleState(
        trackList: List<Track> = state.value.trackList,
        isNothingFound: Boolean = state.value.isNothingFound,
        isNetworkError: Boolean = state.value.isNetworkError,
        isLoading: Boolean = state.value.isLoading
    ) {
       _state.update {
           it.copy(
               trackList = trackList,
               isNothingFound = isNothingFound,
               isNetworkError = isNetworkError,
               isLoading = isLoading
           )
       }
    }
}