package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.db.DatabaseRepository
import com.example.playlistmaker.media.domain.db.FavTracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavTracksInteractorImpl(
    private val databaseRepository: DatabaseRepository
): FavTracksInteractor {

    override fun favouriteTracks(): Flow<List<Track>> {
        return databaseRepository.getFavTracksList()
    }
}