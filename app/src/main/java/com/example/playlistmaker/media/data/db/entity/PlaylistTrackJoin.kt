package com.example.playlistmaker.media.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "playlist_track_join",
    primaryKeys = ["playlistId", "trackId"],
    foreignKeys = [
        ForeignKey(
            entity = PlaylistEntity::class,
            parentColumns = ["playlistId"],
            childColumns = ["playlistId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TrackEntity::class,
            parentColumns = ["trackId"],
            childColumns = ["trackId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PlaylistTrackJoin(
    val playlistId: Int,
    val trackId: Int
)
