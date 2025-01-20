package com.example.playlistmaker.media.data.temporary

import androidx.room.Embedded
import androidx.room.Relation
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.TrackEntity

data class PlaylistWithTracks(
    @Embedded val playlistEntity: PlaylistEntity,
    @Relation(
        parentColumn = "playlistId",
        entityColumn = "trackId"
    )
    val tracks: List<TrackEntity>
)
