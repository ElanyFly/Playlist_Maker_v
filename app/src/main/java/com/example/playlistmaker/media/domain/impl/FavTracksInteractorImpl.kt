package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.db.DatabaseRepository
import com.example.playlistmaker.media.domain.db.FavTracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavTracksInteractorImpl(
    private val databaseRepository: DatabaseRepository
) : FavTracksInteractor {

    override fun getFavTracksList(): Flow<List<Track>> {
        return databaseRepository.getFavTracksList()
    }

    override suspend fun addTrackToFav(track: Track) {
        databaseRepository.addTrackToFav(track)
    }

    override suspend fun deleteTrackFromFav(track: Track) {
        databaseRepository.deleteTrackFromFav(track)
    }

    override suspend fun getFavStatus(trackId: Int): Boolean {
        return databaseRepository.getFavStatus(trackId)
    }

}