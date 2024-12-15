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
inline fun <reified T> retrofit2.Response<T>.call(): Response<T> {


    val body = this.body() ?: return Response.Error(
        errorMessage = this.message(),
        errorCode = this.code()
    )

    return Response.Success(
        data = body,
        responseCode = this.code()
    )

}