package com.example.playlistmaker.media.data

import com.example.playlistmaker.media.data.convertors.toTrack
import com.example.playlistmaker.media.data.convertors.toTrackEntity
import com.example.playlistmaker.media.data.db.FavTracksDatabase
import com.example.playlistmaker.media.domain.db.DatabaseRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class DatabaseRepositoryImpl(
    private val favTracksDatabase: FavTracksDatabase
): DatabaseRepository {

    override suspend fun addTrackToFav(track: Track) {
        withContext(Dispatchers.IO) {
            favTracksDatabase.trackDao().addTrackToFav(track.toTrackEntity())
        }
    }

    override suspend fun deleteTrackFromFav(track: Track) {
        withContext(Dispatchers.IO) {
            favTracksDatabase.trackDao().deleteTrackFromFav(track.toTrackEntity())
        }
    }

    override fun getFavTracksList(): Flow<List<Track>> = flow {
        val trackList = favTracksDatabase.trackDao().getAllTracksInFav()
        emit(trackList.map { trackEntity ->
            trackEntity.toTrack()
        })
    }
}