package com.example.playlistmaker.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.SearchResult
import com.example.playlistmaker.search.domain.models.Track

class SearchViewModel(
    private val searchInteractor: SearchInteractor
) : ViewModel() {

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
        val historyList = searchInteractor.restoreHistoryCache()
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

        searchInteractor.searchTrack(
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
        searchInteractor.clearTrackHistory()
        handleState(
            trackList = emptyList(),
            isNothingFound = false,
            isNetworkError = false,
            isLoading = false,
            isHistoryShown = false
        )
    }

    private fun handleAddTrackToHistory(action: SearchAction.AddTrackToHistoryList) {
        searchInteractor.addTrackToHistory(action.track)
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
        ) ?: return

        _state.postValue(newValue)
    }
}