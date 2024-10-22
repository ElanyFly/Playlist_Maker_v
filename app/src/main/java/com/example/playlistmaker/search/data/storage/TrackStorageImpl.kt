package com.example.playlistmaker.search.data.storage

import android.util.Log
import com.example.playlistmaker.search.domain.SharedPreferencesHistory
import com.example.playlistmaker.search.domain.api.TrackStorage
import com.example.playlistmaker.search.domain.models.Track

class TrackStorageImpl(
    private val sharedPreferencesHistory: SharedPreferencesHistory
): TrackStorage {

    private var historyList: List<Track> = sharedPreferencesHistory.getHistory()
    
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