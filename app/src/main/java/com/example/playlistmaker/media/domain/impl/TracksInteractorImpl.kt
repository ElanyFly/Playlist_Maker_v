package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.db.TracksRepository
import com.example.playlistmaker.media.domain.db.TracksInteractor
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class TracksInteractorImpl(
    private val tracksRepository: TracksRepository
) : TracksInteractor {

    override fun getFavTracksList(): Flow<List<Track>> {
        return tracksRepository.getFavTracksList()
    }

    override suspend fun addTrack(track: Track) {
        tracksRepository.addTrack(track)
    }

    override suspend fun deleteTrackById(trackId: Int) {
        tracksRepository.deleteTrackById(trackId)
    }

    override suspend fun updateFavouriteStatus(trackId: Int, isFavourite: Boolean) {
        tracksRepository.updateFavouriteStatus(trackId, isFavourite)
    }

//    override suspend fun deleteTrackFromFav(track: Track) {
//        addTrack(track.copy(isFavorite = false))
//    }

    override suspend fun getFavStatus(trackId: Int): Boolean {
        return tracksRepository.getFavStatus(trackId)
    }

    override suspend fun addIfNoTrack(track: Track) {
        return tracksRepository.addIfNoTrack(track)
    }

}