package com.example.playlistmaker.search.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.search.domain.SharedPreferencesHistory
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.serialize
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class SharedPreferencesHistoryImpl(
    private val context: Context
) : SharedPreferencesHistory {
    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    private val prefLock = ReentrantReadWriteLock()

    override fun saveHistory(historyList: List<Track>) {
        val listAsString: String = historyList.serialize()
        prefLock.write {
            sp.edit()
                .putString(HISTORY, listAsString)
                .apply()
        }
    }

    override fun getHistory(): List<Track> {
        return prefLock.read {
            sp.getString(HISTORY, null)
                ?.deserialize<Array<Track>>()?.toList() ?: emptyList()
        }
    }

    companion object {
        private const val PREFERENCES = "app_preferences_history"
        private const val HISTORY = "history"
    }
}