package com.example.playlistmaker.search.domain.impl

import com.example.playlistmaker.search.domain.SearchInteractor
import com.example.playlistmaker.search.domain.SearchResult
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.CoroutineScopes
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchInteractorImpl(
    private val trackRepository: TrackRepository,
) : SearchInteractor {

    private var previousQuery = ""


    override suspend fun searchTrack(
        query: String,
        isRefreshed: Boolean,
    ): Flow<SearchResult>? {
        if ((previousQuery == query && !isRefreshed) || query.isEmpty()) {
            return null
        }
        previousQuery = query

        val result = trackRepository.searchTracks(query)?.map { tracks ->
            when {
                tracks.isLoading -> SearchResult.Loading
                tracks.isError -> SearchResult.Error(isNetworkError = true)
                tracks.trackList.isEmpty() -> SearchResult.Error(isNothingFound = true)
                else -> SearchResult.Success(trackList = tracks.trackList)
            }

        }
        previousQuery = ""
        return result

    }

    override suspend fun clearTrackHistory() {
        trackRepository.clearHistoryList()
    }

    override suspend fun addTrackToHistory(track: Track) {
        trackRepository.addTrackToList(track)
    }

    override suspend fun restoreHistoryCache(): List<Track> {
        return trackRepository.getHistoryList()
    }

}
