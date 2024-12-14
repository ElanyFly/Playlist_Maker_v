package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.SearchResult
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.CoroutineScopes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch

class SearchInteractorImpl(
    private val trackRepository: TrackRepository,
    private val scopes: CoroutineScopes
) : SearchInteractor {

    private var previousQuery = ""

    private var currentJob: Job? = null

    override suspend fun searchTrack(
        query: String,
        isRefreshed: Boolean,
    ): Flow<SearchResult>? {
        if ((previousQuery == query && !isRefreshed) || query.isEmpty()) {
            return null
        }
        previousQuery = query

        return channelFlow <SearchResult> {
            send(SearchResult.Loading)
            currentJob?.cancel()
            currentJob = scopes.ioScope.launch {
                val tracks = trackRepository.searchTracks(query)
                send(
                    when {
                        tracks.isError -> SearchResult.Error(isNetworkError = true)
                        tracks.trackList.isEmpty() -> SearchResult.Error(isNothingFound = true)
                        else -> SearchResult.Success(trackList = tracks.trackList)
                    }
                )
                previousQuery = ""
            }
            currentJob?.join()
        }
    }

    override fun clearTrackHistory() {
        trackRepository.clearHistoryList()
    }

    override fun addTrackToHistory(track: Track) {
        trackRepository.addTrackToList(track)
    }

    override fun restoreHistoryCache(): List<Track> {
        return trackRepository.getHistoryList()
    }

}
