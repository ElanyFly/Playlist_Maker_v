package com.example.playlistmaker.search.domain.models

sealed class Response<out T>(val responseCode: Int) {
    class Success<T>(val data: T, responseCode: Int) : Response<T>(responseCode = responseCode)
    class Error(val errorMessage: String, val errorCode: Int) : Response<Nothing>(responseCode = errorCode)
}