package com.orogersilva.myweatherforecast.data.domain

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data
