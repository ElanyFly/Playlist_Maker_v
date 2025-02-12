package com.example.playlistmaker.media.data

import com.example.playlistmaker.media.data.convertors.toTrack
import com.example.playlistmaker.media.data.convertors.toTrackEntity
import com.example.playlistmaker.media.data.db.TracksDatabase
import com.example.playlistmaker.media.domain.db.TracksRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class TracksRepositoryImpl(
    private val tracksDatabase: TracksDatabase
) : TracksRepository {

    override suspend fun addTrack(track: Track) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().addTrack(track.toTrackEntity())
        }
    }

    override suspend fun addIfNoTrack(track: Track) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().addIfNoTrack(track.toTrackEntity())
        }
    }

    override suspend fun deleteTrackById(trackId: Int) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().deleteTrackById(trackId)
        }
    }

    override suspend fun updateFavouriteStatus(trackId: Int, isFavourite: Boolean) {
        withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().updateFavouriteStatus(trackId, isFavourite)
        }
    }

    override suspend fun isTrackExists(trackId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().isTrackExists(trackId)
        }
    }

//    override suspend fun deleteTrackFromFav(track: Track) {
//        withContext(Dispatchers.IO) {
//            tracksDatabase.trackDao().deleteTrackFromFav(track.toTrackEntity())
//        }
//    }

    override fun getFavTracksList(): Flow<List<Track>> = flow {
        val trackList = tracksDatabase.trackDao().getAllTracksInFav()
        emit(trackList.map { trackEntity ->
            trackEntity.toTrack()
        })
    }

    override suspend fun getFavStatus(trackId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            tracksDatabase.trackDao().getFavStatus(trackId)
        }
    }
}