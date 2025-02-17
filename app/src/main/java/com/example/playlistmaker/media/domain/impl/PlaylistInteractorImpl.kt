package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.PlaylistRepository
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val playlistRepository: PlaylistRepository
) : PlaylistInteractor {

    override suspend fun createNewPlaylist(playlist: PlaylistModel) {
        playlistRepository.createNewPlaylist(playlist)
    }

    override suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoinModel): Boolean {
        return playlistRepository.insertConnection(playlistTrackJoin)
    }

    override suspend fun deletePlaylist(playlistId: Int) {
        playlistRepository.deletePlaylist(playlistId)
    }

    override suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoinModel) {
        playlistRepository.deleteConnection(playlistTrackJoin)
    }

    override suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksModel>> {
        return playlistRepository.getAllPlaylists()
    }

    override suspend fun getPlaylistWithTracks(playlistId: Int): Flow<PlaylistWithTracksModel> {
        return playlistRepository.getPlaylistWithTracks(playlistId)
    }

    override suspend fun isTrackInAnyPlaylist(trackId: Int): Boolean {
        return playlistRepository.isTrackInAnyPlaylist(trackId)
    }
}