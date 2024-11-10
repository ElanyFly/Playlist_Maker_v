package com.example.playlistmaker.di

import com.example.playlistmaker.search.data.storage.SharedPreferencesHistoryImpl
import com.example.playlistmaker.search.domain.SharedPreferencesHistory
import com.example.playlistmaker.settings.data.storage.SharedPreferencesThemeImpl
import com.example.playlistmaker.settings.domain.SharedPreferencesTheme
import org.koin.dsl.module

val sharedPrefModule = module {
    single<SharedPreferencesTheme> {
        SharedPreferencesThemeImpl(
            context = get()
        )
    }

    single<SharedPreferencesHistory> {
        SharedPreferencesHistoryImpl(
            context = get()
        )
    }
}
