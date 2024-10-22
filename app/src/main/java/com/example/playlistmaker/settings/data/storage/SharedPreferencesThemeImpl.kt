package com.example.playlistmaker.settings.data.storage

import android.content.Context
import android.content.SharedPreferences
import com.example.playlistmaker.settings.domain.SharedPreferencesTheme
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class SharedPreferencesThemeImpl(
    private val context: Context
) : SharedPreferencesTheme {
    private val sp: SharedPreferences by lazy {
        context.getSharedPreferences(
            PREFERENCES,
            Context.MODE_PRIVATE
        )
    }

    private val prefLock = ReentrantReadWriteLock()

    override fun saveSwitchState(isEnabled: Boolean) {
        prefLock.write {
            sp.edit().putBoolean(SWITCH, isEnabled).apply()
        }
    }

    override fun getSwitchState(): Boolean {
        return prefLock.read {
            sp.getBoolean(SWITCH, false)
        }
    }


    companion object {
        private const val PREFERENCES = "app_preferences_history"
        private const val SWITCH = "switchState"
    }
}