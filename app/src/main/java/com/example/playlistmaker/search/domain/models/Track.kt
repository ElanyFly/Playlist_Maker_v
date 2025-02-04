package com.example.playlistmaker.search.domain.models


data class Track(
    val trackId: Int,
    val trackName: String,
    val artistName: String,
    val trackTime: Long,
    val pictureURL: String,
    val collectionName: String,
    val releaseDate: String,
    val primaryGenreName: String,
    val country: String,
    val previewUrl: String,
    var isFavorite: Boolean = false

)

