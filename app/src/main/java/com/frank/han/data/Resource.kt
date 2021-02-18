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

    /**
     * Loading
     *
     * @param T type of data
     * @constructor creates Loading
     */
    class Loading<T>(data: T? = null) : Resource<T>(data)

    /**
     * Success
     *
     * @param T type of data
     * @constructor creates Success
     */
    class Success<T>(data: T) : Resource<T>(data)

    /**
     * Failed or Error
     *
     * @param T type of data
     * @constructor creates Errors
     */
    class Errors<T>(errorInfo: ErrorInfo, data: T? = null) : Resource<T>(data, errorInfo)
}
