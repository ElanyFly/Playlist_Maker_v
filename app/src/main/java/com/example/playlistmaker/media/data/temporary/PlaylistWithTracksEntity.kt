package com.example.playlistmaker.media.data.temporary

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.TrackEntity

data class PlaylistWithTracksEntity(
    @Embedded val playlistEntity: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId",
        associateBy = Junction(PlaylistTrackJoin::class)
    )
    val tracks: List<TrackEntity>
)
