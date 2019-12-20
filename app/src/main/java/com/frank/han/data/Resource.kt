package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.frank.han.data.Resource.Errors
import com.frank.han.data.Resource.Loading
import com.frank.han.data.Resource.Success

/**
 * A generic class that contains data and status about loading this data.
 *
 * @author frank
 * @date 2019/12/9 11:11 AM
 */
sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Success<T>(data: T) : Resource<T>(data)
    class Errors<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

fun <S, D> resMapping(src: Resource<S>, mapping: (S) -> D): Resource<D> = when (src) {
    is Loading -> Loading(src.data?.let(mapping))
    is Success -> Success(mapping(src.data!!))
    is Errors -> Errors(src.message!!, src.data?.let(mapping))
}

suspend fun <T> getRemoteResource(call: suspend () -> T): Resource<T> = try {
    Resource.Success(call.invoke())
} catch (e: Exception) {
    Resource.Errors(e.message!!)
}

suspend fun <T> getLocalResource(query: () -> LiveData<T>): LiveData<Resource<T>>? = try {
    query.invoke().map { Resource.Success(it) }
} catch (e: Exception) {
    null
}
