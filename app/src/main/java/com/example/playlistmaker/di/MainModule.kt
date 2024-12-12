package com.example.playlistmaker.di

import com.example.playlistmaker.utils.CoroutineScopes
import org.koin.dsl.module

val mainModule = module {

    single<CoroutineScopes> {
        CoroutineScopes()
    }

}