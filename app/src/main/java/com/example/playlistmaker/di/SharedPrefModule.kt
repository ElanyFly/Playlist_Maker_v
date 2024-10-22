package com.example.playlistmaker.di

import com.example.playlistmaker.common.SharedPreferencesManager
import org.koin.dsl.module

val sharedPrefModule = module {
    single<SharedPreferencesManager> {
        SharedPreferencesManager(
            context = get()
        )
    }
}
