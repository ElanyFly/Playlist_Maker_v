package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.db.TracksRepository
import com.example.playlistmaker.media.domain.db.FavTracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavTracksInteractorImpl(
    private val tracksRepository: TracksRepository
) : FavTracksInteractor {

    override fun getFavTracksList(): Flow<List<Track>> {
        return tracksRepository.getFavTracksList()
    }

    override suspend fun addTrackToFav(track: Track) {
        tracksRepository.addTrackToFav(track)
    }

    override suspend fun deleteTrackFromFav(track: Track) {
        tracksRepository.deleteTrackFromFav(track)
    }

    override suspend fun getFavStatus(trackId: Int): Boolean {
        return tracksRepository.getFavStatus(trackId)
    }

}