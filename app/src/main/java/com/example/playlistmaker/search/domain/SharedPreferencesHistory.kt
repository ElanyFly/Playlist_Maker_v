package com.example.playlistmaker.search.domain

import com.example.playlistmaker.search.domain.models.Track

interface SharedPreferencesHistory {
    fun saveHistory(historyList: List<Track>)
    fun getHistory(): List<Track>
}