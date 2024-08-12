package com.example.playlistmaker

import android.util.Log

object HistoryStore {
    private const val MAX_SIZE = 10
    private var historyList: List<Track> = SharedPreferencesManager.instance.getHistory()

    fun getHistoryList(): List<Track> {
        return historyList
    }
    fun addTrackToList(track: Track) {
        val oldList = historyList
        val mutableHistoryList = historyList
            .removeTrackRepeat(track)
            .toMutableList()

        mutableHistoryList.add(0, track)
        historyList = mutableHistoryList.take(MAX_SIZE)
        Log.i("addTrack", "historyList - $historyList")
        if (oldList != historyList) {
            SharedPreferencesManager.instance.saveHistory(historyList)
        }

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

    fun clearHistoryList() {
        historyList = emptyList()
        SharedPreferencesManager.instance.saveHistory(historyList)
    }
}