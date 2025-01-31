package com.example.playlistmaker.media.data.db.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class PlaylistWithTracksEntity(
    @Embedded val playlistEntity: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackJoin::class)
    )
    val tracks: List<TrackEntity>
)
