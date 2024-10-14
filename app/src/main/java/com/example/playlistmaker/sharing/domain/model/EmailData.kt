package com.example.playlistmaker.sharing.domain.model

data class EmailData(
    val emailAddress: Array<String>,
    val header: String,
    val message: String,
)