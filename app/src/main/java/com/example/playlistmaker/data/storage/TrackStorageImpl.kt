package com.example.playlistmaker.data.storage

import android.util.Log
import com.example.playlistmaker.domain.api.TrackStorage
import com.example.playlistmaker.domain.models.Track

class TrackStorageImpl(sharedPreferencesManager: SharedPreferencesManager): TrackStorage {
    private var historyList: List<Track> = SharedPreferencesManager.instance.getHistory()
    
    override fun clearHistoryList() {
        historyList = emptyList()
        SharedPreferencesManager.instance.saveHistory(historyList)
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
            SharedPreferencesManager.instance.saveHistory(historyList)
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