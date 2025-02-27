package com.example.playlistmaker.media.data

import com.example.playlistmaker.media.data.convertors.toPlaylistEntity
import com.example.playlistmaker.media.data.convertors.toPlaylistModel
import com.example.playlistmaker.media.data.convertors.toPlaylistTrackJoinEntity
import com.example.playlistmaker.media.data.convertors.toPlaylistWithTracksModel
import com.example.playlistmaker.media.data.db.TracksDatabase
import com.example.playlistmaker.media.domain.db.PlaylistRepository
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.withContext

class PlaylistRepositoryImpl(
    private val tracksDatabase: TracksDatabase
) : PlaylistRepository {

    override suspend fun createNewPlaylist(playlist: PlaylistModel) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao().createNewPlaylist(playlist.toPlaylistEntity())
        }
    }

    override suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoinModel): Boolean {
        return withContext(Dispatchers.IO) {
            val isExist = tracksDatabase.playlistDao().isConnectionExists(
                playlistId = playlistTrackJoin.playlistId,
                trackId = playlistTrackJoin.trackId
            )
            tracksDatabase.playlistDao()
                .insertConnection(playlistTrackJoin.toPlaylistTrackJoinEntity())
            isExist
        }
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao().deletePlaylist(playlistId)
        }
    }

    override suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoinModel) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao()
                .deleteConnection(playlistTrackJoin.toPlaylistTrackJoinEntity())
        }
    }

    override suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksModel>> = flow {
        val playlist =
            tracksDatabase.playlistDao().getAllPlaylists()
                .mapNotNull { it.toPlaylistWithTracksModel() }
        emit(playlist)
    }

    override suspend fun getPlaylistWithTracks(playlistId: Int): Flow<PlaylistWithTracksModel> {
        return tracksDatabase.playlistDao().getPlaylistWithTracks(playlistId).mapNotNull {
            it.toPlaylistWithTracksModel()
        }
    }

    override suspend fun isTrackInAnyPlaylist(trackId: Int): Boolean {
        return tracksDatabase.playlistDao().isTrackInAnyPlaylist(trackId)
    }

    override suspend fun updatePlaylist(playlist: PlaylistModel) {
        withContext(Dispatchers.IO) {
            tracksDatabase.playlistDao().updatePlaylist(playlist.toPlaylistEntity())
        }
    }

    override suspend fun getPlaylistById(playlistId: Int): PlaylistModel {
        return tracksDatabase.playlistDao().getPlaylistById(playlistId).toPlaylistModel()

    }

}

