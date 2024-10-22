package com.example.playlistmaker.common

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.search.domain.models.Track
import com.example.playlistmaker.utils.deserialize
import com.example.playlistmaker.utils.serialize
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class SharedPreferencesManager(
    private val context: Context
) {
    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    private val prefLock = ReentrantReadWriteLock()

    fun saveSwitchState(isEnabled: Boolean) {
        prefLock.write {
            sp.edit().putBoolean(SWITCH, isEnabled).apply()
        }
    }

    fun getSwitchState(): Boolean {
        return prefLock.read {
            sp.getBoolean(SWITCH, false)
        }
    }

    fun saveHistory(historyList: List<Track>) {
        val listAsString: String = historyList.serialize()
        prefLock.write {
            sp.edit()
                .putString(HISTORY, listAsString)
                .apply()
        }
    }

    fun getHistory(): List<Track> {
        return prefLock.read {
            sp.getString(HISTORY, null)
                ?.deserialize<Array<Track>>()?.toList() ?: emptyList()
        }
    }

    companion object {
        private const val PREFERENCES = "app_preferences"
        private const val SWITCH = "switchState"
        private const val HISTORY = "history"
    }
}
