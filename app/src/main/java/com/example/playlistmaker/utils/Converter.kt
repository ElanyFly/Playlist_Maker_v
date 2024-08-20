package com.example.playlistmaker.utils

import java.text.SimpleDateFormat
import java.util.Locale


fun Long.convertMS(): String {
    return SimpleDateFormat("mm:ss", Locale.getDefault()).format(this)
}