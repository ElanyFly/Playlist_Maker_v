package com.example.playlistmaker.search.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.creator.Creator
import com.example.playlistmaker.search.domain.SearchResult
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SearchActivityViewModel : ViewModel() {

    private val searchInteraction = Creator.searchInteractionProvide()
//    private val _state = MutableStateFlow<SearchActivityState>(SearchActivityState.defaultState)
//    val state = _state.asStateFlow()

    private val _state = MutableLiveData<SearchActivityState>(SearchActivityState.defaultState)
    val state: LiveData<SearchActivityState>
        get() = _state



    fun makeAction(action: SearchAction) {
        when (action) {
            is SearchAction.AddTrackToHistoryList -> handleAddTrackToHistory(action)
            is SearchAction.ClearTrackHistory -> handleClearTrackHistory()
            is SearchAction.SearchTrack -> handleSearchTrack(action)
            is SearchAction.RestoreHistoryCache -> handleRestoreHistoryCache()
            is SearchAction.ClearSearchQuery -> handleRestoreHistoryCache()
        }
    }

    private fun handleRestoreHistoryCache() {
        val historyList = searchInteraction.restoreHistoryCache()
        if (historyList.isEmpty()) {
            return
        }
        handleState(
            trackList = historyList,
            isNothingFound = false,
            isNetworkError = false,
            isLoading = false,
            isHistoryShown = true
        )
    }

    private fun handleSearchTrack(action: SearchAction.SearchTrack) {

        searchInteraction.searchTrack(
            query = action.inputQuery,
            isRefreshed = action.isRefreshed,
            resultLambda = { searchResult ->
                when (searchResult) {
                    is SearchResult.Error -> handleState(
                        isLoading = false,
                        isNothingFound = searchResult.isNothingFound,
                        isNetworkError = searchResult.isNetworkError,
                        trackList = emptyList(),
                        isHistoryShown = false
                    )

                    is SearchResult.Success -> handleState(
                        isLoading = false,
                        trackList = searchResult.trackList,
                        isNetworkError = false,
                        isNothingFound = false,
                        isHistoryShown = false
                    )

                    is SearchResult.Loading -> handleState(
                        isLoading = true,
                        isNetworkError = false,
                        isNothingFound = false,
                        trackList = emptyList(),
                        isHistoryShown = false
                    )
                }
            }
        )
    }

    private fun handleClearTrackHistory() {
        searchInteraction.clearTrackHistory()
        handleState(
            trackList = emptyList(),
            isNothingFound = false,
            isNetworkError = false,
            isLoading = false,
            isHistoryShown = false
        )
    }

    private fun handleAddTrackToHistory(action: SearchAction.AddTrackToHistoryList) {
        searchInteraction.addTrackToHistory(action.track)
        if (state.value?.isHistoryShown == true){
            handleRestoreHistoryCache()
        }

    }

    @Synchronized
    private fun handleState(
        trackList: List<Track> = state.value?.trackList ?: emptyList(),
        isNothingFound: Boolean = state.value?.isNothingFound ?: false,
        isNetworkError: Boolean = state.value?.isNetworkError ?: false,
        isLoading: Boolean = state.value?.isLoading ?: false,
        isHistoryShown: Boolean = state.value?.isHistoryShown ?: false
    ) {
        val newValue = _state.value?.copy(
            trackList = trackList,
            isNothingFound = isNothingFound,
            isNetworkError = isNetworkError,
            isLoading = isLoading,
            isHistoryShown = isHistoryShown
        )

        _state.postValue(newValue)
    }
}