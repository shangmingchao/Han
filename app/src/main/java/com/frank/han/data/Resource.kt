package com.frank.han.data

/**
 * A generic class that contains data and status about loading this data.
 *
 * @author frank
 * @date 2019/12/9 11:11 AM
 */
sealed class Resource<out T>

/**
 * Loading
 *
 * @param T type of data
 * @constructor creates Loading
 */
class Loading<T>(val data: T? = null) : Resource<T>()

/**
 * Success
 *
 * @param T type of data
 * @constructor creates Success
 */
class Success<T>(val data: T) : Resource<T>()

/**
 * Failed or Error
 *
 * @param T type of data
 * @constructor creates Errors
 */
class Error<T>(val errorInfo: ErrorInfo, val data: T? = null) : Resource<T>()
