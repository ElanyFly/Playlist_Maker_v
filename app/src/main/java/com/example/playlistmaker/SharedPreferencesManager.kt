package com.example.playlistmaker

import android.content.Context
import android.content.SharedPreferences
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

object SharedPreferencesManager{
    private const val PREFERENCES = "app_preferences"
    private const val SWITCH = "switchState"

    private val sp: SharedPreferences by lazy {
        App.applicationContext.getSharedPreferences(
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

}
