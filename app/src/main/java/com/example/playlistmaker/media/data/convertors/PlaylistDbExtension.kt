package com.example.playlistmaker.media.data.convertors

import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.temporary.PlaylistModel
import com.example.playlistmaker.media.data.temporary.PlaylistWithTracksEntity

fun PlaylistModel.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        playlistId = playlistId,
        playListName = playListName,
        playListDescription = playListDescription,
        coverUri = coverUri,
        playlistTrackIds = playlistTracks.map { it.trackId },
        playlistTrackAmount = playlistTrackAmount,
    )
}

fun PlaylistWithTracksEntity.toPlaylistModel(): PlaylistModel {
    return PlaylistModel(
        playlistId = playlistEntity.playlistId,
        playListName = playlistEntity.playListName,
        playListDescription = playlistEntity.playListDescription,
        coverUri = playlistEntity.coverUri,
        playlistTracks = tracks.map { it.toTrack() },
        playlistTrackAmount = playlistEntity.playlistTrackAmount
    )
}