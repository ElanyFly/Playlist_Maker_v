package com.example.playlistmaker.sharing.data.utils

import android.content.Context
import com.example.playlistmaker.R
import com.example.playlistmaker.media.domain.db.model.PlaylistWithTracksModel
import com.example.playlistmaker.utils.convertMS
import org.koin.java.KoinJavaComponent.inject

private val context: Context by inject(
    clazz = Context::class.java
)

fun PlaylistWithTracksModel.toPlaylistShare(): String {
    val trackSize = playlistTracks.size
    val pluralTracks = context.resources.getQuantityString(
        R.plurals.track_count,
        trackSize,
        trackSize
    )
    val tracksText = playlistTracks.mapIndexed { index, track ->
        "${index + 1}. ${track.artistName} - ${track.trackName} (${track.trackTime.convertMS()})"
    }
        .joinToString("\n")

    val finalText = """
        Название плейлиста: $playListName
        Описание: $playListDescription
        $pluralTracks
        
        $tracksText
    """.trimIndent()

    return finalText
}