package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.SearchInteraction
import com.example.playlistmaker.search.domain.SearchResult
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.api.TrackStorage
import com.example.playlistmaker.search.domain.models.Track
import kotlin.concurrent.thread

class SearchInteractionImpl(
    private val trackRepository: TrackRepository,
    private val trackStorage: TrackStorage
) : SearchInteraction {

    private var previousQuery = ""
    private var currentThread: Thread? = null

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
        currentThread?.interrupt()
        currentThread = thread {
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
