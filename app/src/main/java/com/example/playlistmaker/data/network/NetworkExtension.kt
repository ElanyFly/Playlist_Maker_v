package com.example.playlistmaker.data.network

import com.example.playlistmaker.domain.models.Response
import retrofit2.Call

inline fun <reified T> Call<T>.call(): Response<T> {

    val result = execute()
    val body = result.body() ?: return Response.onError(
        errorMessage = result.message(),
        errorCode = result.code()
    )

    return Response.onSuccess(
        data = body,
        responseCode = result.code()
    )

}