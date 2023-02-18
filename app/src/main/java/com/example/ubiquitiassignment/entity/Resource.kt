package com.example.ubiquitiassignment.entity

sealed class Resource<T> {
    data class Loading<T>(val data: T? = null) : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val data: T? = null, val message: String) : Resource<T>()

    @JvmOverloads
    fun result(
        onLoading: ((isLoading: Boolean, data: T?) -> Unit)? = null,
        onSuccess: ((data: T) -> Unit)? = null,
        onError: ((data: T?, errorMsg: String) -> Unit)? = null
    ) {
        when (this) {
            is Loading -> onLoading?.invoke(true, data)
            is Success -> {
                onLoading?.invoke(false, data)
                onSuccess?.invoke(data)
            }
            is Error -> {
                onLoading?.invoke(false, data)
                onError?.invoke(data, message)
            }
        }
    }
}
