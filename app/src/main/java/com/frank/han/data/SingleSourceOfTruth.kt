package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

const val CODE_UNKNOWN = 0
const val CODE_SOCKET_TIMEOUT = 1
const val CODE_UNKNOWN_HOST = 2
const val CODE_DB_ERROR = 3

/**
 *
 * [P] refer to the PO object of database, [D] refer to the DTO object of network,
 * [V] refer to the VO object of UI
 * @author frank
 * @date 2019/12/9 11:06 AM
 */
fun <V, D, P> getResource(
    databaseQuery: () -> Flow<P>,
    networkCall: suspend () -> D,
    dpMapping: (D) -> P,
    pvMapping: (P) -> V,
    saveCallResult: suspend (P) -> Unit
): LiveData<Resource<V>> = liveData(Dispatchers.IO, 0) {
    emit(Resource.Loading())
    val localResource = getLocalResource(databaseQuery)
    emitSource(localResource.map { resMapping(it, pvMapping) })
    val remoteResource = getRemoteResource(networkCall)
    if (remoteResource is Resource.Success) {
        saveCallResult.invoke(dpMapping(remoteResource.data!!))
    } else if (remoteResource is Resource.Errors) {
        emit(Resource.Errors(remoteResource.errorInfo!!))
        emitSource(localResource.map { resMapping(it, pvMapping) })
    }
}

fun <S, D> resMapping(src: Resource<S>, mapping: (S) -> D): Resource<D> = when (src) {
    is Resource.Loading -> Resource.Loading(src.data?.let(mapping))
    is Resource.Success -> Resource.Success(mapping(src.data!!))
    is Resource.Errors -> Resource.Errors(src.errorInfo!!, src.data?.let(mapping))
}

suspend fun <T> getRemoteResource(call: suspend () -> T): Resource<T> = try {
    Resource.Success(call.invoke())
} catch (e: Exception) {
    when (e) {
        is SocketTimeoutException -> Resource.Errors(
            ErrorInfo.NetError(
                CODE_SOCKET_TIMEOUT,
                e.message!!
            )
        )
        is UnknownHostException -> Resource.Errors(
            ErrorInfo.NetError(
                CODE_UNKNOWN_HOST,
                e.message!!
            )
        )
        else -> Resource.Errors(ErrorInfo.OtherError(CODE_UNKNOWN, e.message!!))
    }
}

fun <T> getLocalResource(query: () -> Flow<T>): LiveData<Resource<T>> = try {
    query.invoke().asLiveData().map { if (it != null) Resource.Success(it) else Resource.Errors<T>(
        ErrorInfo.DBError("empty")
    )
    }
} catch (e: Exception) {
    MutableLiveData(Resource.Errors(ErrorInfo.DBError(e.message!!)))
}
