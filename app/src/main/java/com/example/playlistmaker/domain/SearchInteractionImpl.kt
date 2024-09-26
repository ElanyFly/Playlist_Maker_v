package com.example.playlistmaker.domain

import com.example.playlistmaker.domain.api.TrackRepository
import com.example.playlistmaker.domain.api.TrackStorage
import com.example.playlistmaker.domain.models.Track
import kotlin.concurrent.thread

class SearchInteractionImpl(
    private val trackRepository: TrackRepository,
    private val trackStorage: TrackStorage
) : SearchInteraction {

    private var previousQuery = ""

    override fun searchTrack(
        query: String,
        isRefreshed: Boolean,
        resultLambda: (SearchResult) -> Unit
    ) {
        if ((previousQuery == query && !isRefreshed) || query.isEmpty()) {
            return
        }
        previousQuery = query

        resultLambda(SearchResult.Loading)
        thread {
            val tracks = trackRepository.searchTracks(query)
            when {
                tracks.isError -> resultLambda(SearchResult.Error(isNetworkError = true))
                tracks.trackList.isEmpty() -> resultLambda(SearchResult.Error(isNothingFound = true))
                else -> resultLambda(SearchResult.Success(trackList = tracks.trackList))
            }
            previousQuery = ""
        }

    }

    override fun clearTrackHistory() {
        trackStorage.clearHistoryList()
    }

    override fun addTrackToHistory(track: Track) {
        trackStorage.addTrackToList(track)
    }

    override fun restoreHistoryCache(): List<Track> {
        return trackStorage.getHistoryList()
    }

}
