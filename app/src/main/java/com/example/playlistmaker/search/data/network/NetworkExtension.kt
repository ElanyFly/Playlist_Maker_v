package com.example.playlistmaker.search.data.network

import com.example.playlistmaker.search.domain.models.Response
import retrofit2.Call

inline fun <reified T> Call<T>.call(): Response<T> {

    val result = execute()
    val body = result.body() ?: return Response.Error(
        errorMessage = result.message(),
        errorCode = result.code()
    )

    return Response.Success(
        data = body,
        responseCode = result.code()
    )

}