package com.example.playlistmaker.search.data.repository

import android.util.Log
import com.example.playlistmaker.media.data.db.TracksDatabase
import com.example.playlistmaker.search.data.mappers.toTrackList
import com.example.playlistmaker.search.data.network.TrackAPIService
import com.example.playlistmaker.search.data.network.call
import com.example.playlistmaker.search.domain.SharedPreferencesHistory
import com.example.playlistmaker.search.domain.api.TrackRepository
import com.example.playlistmaker.search.domain.models.Response
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.search.domain.models.Tracks
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(
    private val apiService: TrackAPIService,
    private val sharedPreferencesHistory: SharedPreferencesHistory,
    private val tracksDatabase: TracksDatabase
) : TrackRepository {

    private var historyList: List<Track> = sharedPreferencesHistory.getHistory()

    override fun searchTracks(inputQuery: String): Flow<Tracks>? {

        return flow <Tracks> {
            emit(Tracks(isLoading = true))
            val response = apiService.searchTracks(inputQuery).call()
            val favourites = getFavTracksInfo()
            emit(

                when(response) {
                    is Response.Error -> Tracks(
                        isError = true
                    )
                    is Response.Success -> Tracks(
                        trackList = response.data.results.toTrackList().map {
                            it.copy(isFavorite = favourites.contains(it.trackId))
                        }
                    )
                }
            )
        }
    }

    private suspend fun getFavTracksInfo(): List<Int> {
        return tracksDatabase.trackDao().getTrackIDsInFav()
    }

    override fun clearHistoryList() {
        historyList = emptyList()
        sharedPreferencesHistory.saveHistory(historyList)
    }

    override fun addTrackToList(track: Track) {
        val oldList = historyList
        val mutableHistoryList = historyList
            .removeTrackRepeat(track)
            .toMutableList()

        mutableHistoryList.add(0, track)
        historyList = mutableHistoryList.take(MAX_SIZE)
        Log.i("addTrack", "historyList - ${historyList}")
        if (oldList != historyList) {
            sharedPreferencesHistory.saveHistory(historyList)
        }
    }

    override fun getHistoryList(): List<Track> {
        return historyList
    }
    private fun List<Track>.removeTrackRepeat(track: Track): List<Track> {
        var trackToRemove: Track? = null

        for (trackCh in this) {
            if (track.trackId == trackCh.trackId) {
                trackToRemove = trackCh
                break
            }
        }

        if (trackToRemove == null) {
            return this
        }
        return this
            .toMutableList()
            .apply { remove(trackToRemove) }
            .toList()
    }

    companion object {
        private const val MAX_SIZE = 10
    }
}