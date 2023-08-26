package com.borislav.searchmyaddress.core.network.utils

sealed interface Response<out T> {
    data class Success<T>(val data: T) : Response<T>
    data class Error(val error: String?, val code: Int? = null) : Response<Nothing>
}