package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import com.frank.han.data.ErrorInfo.*
import com.frank.han.data.Resource.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.coroutines.flow.Flow

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

fun <S, D> resMapping(src: Resource<S>, mapping: (S) -> D): Resource<D> = when (src) {
    is Loading -> Loading(src.data?.let(mapping))
    is Success -> Success(mapping(src.data!!))
    is Errors -> Errors(src.errorInfo!!, src.data?.let(mapping))
}

suspend fun <T> getRemoteResource(call: suspend () -> T): Resource<T> = try {
    Success(call.invoke())
} catch (e: Exception) {
    when (e) {
        is SocketTimeoutException -> Errors(NetError(CODE_SOCKET_TIMEOUT, e.message!!))
        is UnknownHostException -> Errors(NetError(CODE_UNKNOWN_HOST, e.message!!))
        else -> Errors(OtherError(CODE_UNKNOWN, e.message!!))
    }
}

fun <T> getLocalResource(query: () -> Flow<T>): LiveData<Resource<T>> = try {
    query.invoke().asLiveData().map { if (it != null) Success(it) else Errors<T>(DBError("empty")) }
} catch (e: Exception) {
    MutableLiveData(Errors(DBError(e.message!!)))
}
