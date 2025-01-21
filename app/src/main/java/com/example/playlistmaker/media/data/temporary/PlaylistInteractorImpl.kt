package com.example.playlistmaker.media.data.temporary

import kotlinx.coroutines.flow.Flow

class PlaylistInteractorImpl(
    private val playlistRepository: PlaylistRepository
) : PlaylistInteractor {

    override suspend fun createNewPlaylist(playlist: PlaylistModel) {
        playlistRepository.createNewPlaylist(playlist)
    }

    override suspend fun updatePlaylist(playlist: PlaylistModel) {
        playlistRepository.updatePlaylist(playlist)
    }

    override suspend fun deletePlaylist(playlist: PlaylistModel) {
        playlistRepository.deletePlaylist(playlist)
    }

    override suspend fun getAllPlaylists(): Flow<List<PlaylistWithTracksEntity>> {
        return playlistRepository.getAllPlaylists()
    }

    override fun getPlaylistTracksIds(): Flow<List<Int>> {
        return playlistRepository.getPlaylistTracksIds()
    }

    override suspend fun getPlaylistWithTracks(playlistId: Int): PlaylistWithTracksEntity {
        return playlistRepository.getPlaylistWithTracks(playlistId)
    }
}