package com.example.playlistmaker.data.dto

sealed class Response<out T>(val responseCode: Int) {
    class onSuccess<T>(val data: T, responseCode: Int) : Response<T>(responseCode = responseCode)
    class onError(val errorMessage: String, val errorCode: Int) : Response<Nothing>(responseCode = errorCode)
}