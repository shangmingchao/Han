package com.frank.han.data

/**
 * A generic class that contains data and status about loading this data.
 *
 * @author frank
 * @date 2019/12/9 11:11 AM
 */
sealed class Resource<out T>(
    val data: T? = null,
    val errorInfo: ErrorInfo? = null
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Errors<T>(errorInfo: ErrorInfo, data: T? = null) : Resource<T>(data, errorInfo)
}
