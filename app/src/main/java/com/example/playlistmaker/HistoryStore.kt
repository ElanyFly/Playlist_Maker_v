package com.example.playlistmaker

import android.util.Log

object HistoryStore {
    private const val MAX_SIZE = 10
    private var historyList: List<Track> = emptyList()

    fun getHistoryList(): List<Track> {
        return historyList
    }
    fun addTrackToList(track: Track) {
        val mutableHistoryList = historyList.toMutableList()
        mutableHistoryList.add(0, track)
        historyList = mutableHistoryList.take(MAX_SIZE)
        Log.i("addTrack", "historyList - $historyList")
//        notifyDataSetChanged()
    }
}