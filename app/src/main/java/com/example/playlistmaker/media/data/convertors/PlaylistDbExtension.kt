package com.example.playlistmaker.media.data.convertors

import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoinEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel

fun PlaylistModel.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        playlistId = playlistId,
        playListName = playListName,
        playListDescription = playListDescription,
        coverUri = coverUri,
        playlistTrackAmount = playlistTrackAmount,
    )
}

fun PlaylistTrackJoinModel.toPlaylistTrackJoinEntity(): PlaylistTrackJoinEntity {
    return PlaylistTrackJoinEntity(
        playlistId = playlistId,
        trackId = trackId
    )
}

fun PlaylistWithTracksEntity.toPlaylistWithTracksModel(): PlaylistWithTracksModel {
    return PlaylistWithTracksModel(
        playlistId = playlistEntity.playlistId,
        playListName = playlistEntity.playListName,
        playListDescription = playlistEntity.playListDescription,
        coverUri = playlistEntity.coverUri,
        playlistTrackAmount = playlistEntity.playlistTrackAmount,
        playlistTracks = tracks.map {
            it.toTrack()
        }
    )
}