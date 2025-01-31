package com.example.playlistmaker.media.data

import com.example.playlistmaker.media.data.convertors.toPlaylistEntity
import com.example.playlistmaker.media.data.db.TracksDatabase
import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoinEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.media.domain.db.PlaylistRepository
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class PlaylistRepositoryImpl(
    private val tracksDatabase: TracksDatabase
) : PlaylistRepository {

    override suspend fun createNewPlaylist(playlist: PlaylistModel) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao().createNewPlaylist(playlist.toPlaylistEntity())
        }
    }

    override suspend fun insertConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity): Boolean {
        return withContext(Dispatchers.IO) {
            val isExist = tracksDatabase.playlistDao().isConnectionExists(
                playlistId = playlistTrackJoinEntity.playlistId,
                trackId = playlistTrackJoinEntity.trackId
            )
            tracksDatabase.playlistDao().insertConnection(playlistTrackJoinEntity)
            isExist
        }
    }

    override suspend fun deletePlaylist(playlist: PlaylistModel) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao().deletePlaylist(playlist.toPlaylistEntity())
        }
    }

    override suspend fun deleteConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao().deleteConnection(playlistTrackJoinEntity)
        }
    }

    override suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>> = flow {
        val playlist = tracksDatabase.playlistDao().getAllPlaylists()
        emit(playlist)
    }

    override suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity {
        return withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao().getPlaylistWithTracks(playlistId)
        }
    }

//    override suspend fun getTracksForPlaylist(playlistId: Int): List<Track> {
//        return withContext(Dispatchers.IO) { emptyList()
////            tracksDatabase.playlistDao().getTracksForPlaylist(playlistId).map { it.toTrack() }
//        }
//    }
//
//    override suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistModel> {
//        return withContext(Dispatchers.IO) { emptyList()
////            tracksDatabase.playlistDao().getPlaylistsForTracks(trackId).map { it.toPlaylistModel() }
//        }
//    }

}

