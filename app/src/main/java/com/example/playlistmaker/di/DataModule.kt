package com.example.playlistmaker.di

import androidx.room.Room
import com.example.playlistmaker.media.data.db.FavTracksDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            FavTracksDatabase::class.java,
            "database.db"
        )
            .build()
    }
}