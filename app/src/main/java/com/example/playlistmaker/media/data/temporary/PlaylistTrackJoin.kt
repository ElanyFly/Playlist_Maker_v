package com.example.playlistmaker.media.data.temporary

import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.playlistmaker.media.data.db.entity.PlaylistEntity
import com.example.playlistmaker.media.data.db.entity.TrackEntity

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
