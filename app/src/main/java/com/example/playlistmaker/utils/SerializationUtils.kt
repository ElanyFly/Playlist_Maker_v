package com.example.playlistmaker.utils

import com.google.gson.Gson

val gson = Gson()

inline fun <reified T> String.deserialize(): T {
    return gson.fromJson(this, T::class.java)
}

fun Any.serialize(): String {
    return gson.toJson(this)
}