package com.example.playlistmaker.media.data.convertors

import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.PlaylistTrackJoinEntity
import com.example.playlistmaker.media.domain.db.model.PlaylistModel
import com.example.playlistmaker.media.data.db.entity.PlaylistWithTracksEntity
import com.example.playlistmaker.media.domain.db.model.PlaylistTrackJoinModel

fun PlaylistModel.toPlaylistEntity(): PlaylistEntity {
    return PlaylistEntity(
        playlistId = playlistId,
        playListName = playListName,
        playListDescription = playListDescription,
        coverUri = coverUri,
        playlistTrackAmount = playlistTrackAmount,
    )
}

fun PlaylistEntity.toPlaylistModel(): PlaylistModel {
    return PlaylistModel(
        playlistId = playlistId,
        playListName = playListName,
        playListDescription = playListDescription,
        coverUri = coverUri,
        playlistTrackAmount = playlistTrackAmount
    )
}

fun PlaylistWithTracksEntity.toPlaylistModel(): PlaylistModel {
    return PlaylistModel(
        playlistId = playlistEntity.playlistId,
        playListName = playlistEntity.playListName,
        playListDescription = playlistEntity.playListDescription,
        coverUri = playlistEntity.coverUri,
        playlistTrackAmount = playlistEntity.playlistTrackAmount
    )
}

fun PlaylistTrackJoinEntity.toPlaylistTrackJoinModel(): PlaylistTrackJoinModel {
    return PlaylistTrackJoinModel(
        playlistId = playlistId,
        trackId = trackId
    )
}

fun PlaylistTrackJoinModel.toPlaylistTrackJoinEntity(): PlaylistTrackJoinEntity {
    return PlaylistTrackJoinEntity(
        playlistId = playlistId,
        trackId = trackId
    )
}