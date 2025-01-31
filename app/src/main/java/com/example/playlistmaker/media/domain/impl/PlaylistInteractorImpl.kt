package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoinEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.PlaylistRepository
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val playlistRepository: PlaylistRepository
) : PlaylistInteractor {

    override suspend fun createNewPlaylist(playlist: PlaylistModel) {
        playlistRepository.createNewPlaylist(playlist)
    }

    override suspend fun insertConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity): Boolean {
        return playlistRepository.insertConnection(playlistTrackJoinEntity)
    }

    override suspend fun deletePlaylist(playlist: PlaylistModel) {
        playlistRepository.deletePlaylist(playlist)
    }

    override suspend fun deleteConnection(playlistTrackJoinEntity: PlaylistTrackJoinEntity) {
        playlistRepository.deleteConnection(playlistTrackJoinEntity)
    }

    override suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>> {
        return playlistRepository.getAllPlaylists()
    }

    override suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity {
        return playlistRepository.getPlaylistWithTracks(playlistId)
    }

//    override suspend fun getTracksForPlaylist(playlistId: Int): List<Track> {
//        return playlistRepository.getTracksForPlaylist(playlistId)
//    }
//
//    override suspend fun getPlaylistsForTracks(trackId: Int): List<PlaylistModel> {
//        return playlistRepository.getPlaylistsForTracks(trackId)
//    }
}