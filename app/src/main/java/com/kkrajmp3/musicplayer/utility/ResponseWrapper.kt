package com.kkrajmp3.musicplayer.utility

sealed class ResponseWrapper <T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = -1,
) {
    class Loading<T>(data: T? = null) : ResponseWrapper<T>(data)
    class Success<T>(data: T) : ResponseWrapper<T>(data)
    class Error<T>(message: String, code: Int? = -1, data: T? = null) : ResponseWrapper<T>(data, message, code)
    class AccessTokenExpired<T>(message: String, code: Int? = -1, data: T? = null) : ResponseWrapper<T>(data, message, code)

}