package com.example.playlistmaker.utils

import com.example.playlistmaker.utils.Constants.DATE_PATTERN_MINUTES_AND_SECONDS
import java.text.SimpleDateFormat
import java.util.Locale



fun Long.convertMS(): String {
    return SimpleDateFormat(DATE_PATTERN_MINUTES_AND_SECONDS, Locale.getDefault()).format(this)
}

