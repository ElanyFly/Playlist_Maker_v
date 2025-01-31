package com.example.playlistmaker.media.domain.impl

import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoin
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.media.domain.db.PlaylistInteractor
import com.example.playlistmaker.media.domain.db.PlaylistRepository
import com.example.playlistmaker.search.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val playlistRepository: PlaylistRepository
) : PlaylistInteractor {

    override suspend fun createNewPlaylist(playlist: PlaylistModel) {
        playlistRepository.createNewPlaylist(playlist)
    }

    override suspend fun insertConnection(playlistTrackJoin: PlaylistTrackJoin): Boolean {
        return playlistRepository.insertConnection(playlistTrackJoin)
    }

    override suspend fun deletePlaylist(playlist: PlaylistModel) {
        playlistRepository.deletePlaylist(playlist)
    }

    override suspend fun deleteConnection(playlistTrackJoin: PlaylistTrackJoin) {
        playlistRepository.deleteConnection(playlistTrackJoin)
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