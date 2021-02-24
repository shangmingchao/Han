package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

const val CODE_UNKNOWN = 0
const val CODE_SOCKET_TIMEOUT = 1
const val CODE_UNKNOWN_HOST = 2
const val CODE_DB_ERROR = 3
const val CODE_HTTP_EXCEPTION = 4

/**
 * Get single source
 *
 * [P] refer to the PO object of database, [D] refer to the DTO object of network,
 * [V] refer to the VO object of UI
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
    emitSource(localResource.map { it.resMapping(pvMapping) })
    val remoteResource = getRemoteResource(networkCall)
    if (remoteResource is Resource.Success) {
        saveCallResult.invoke(dpMapping(remoteResource.data!!))
    } else if (remoteResource is Resource.Errors) {
        emit(Resource.Errors(remoteResource.errorInfo!!))
        emitSource(localResource.map { it.resMapping(pvMapping) })
    }
}

/**
 * Mapping Resource
 *
 * @receiver Resource<S>
 * @param mapping Function1<S, D>
 * @return Resource<D>
 */
fun <S, D> Resource<S>.resMapping(mapping: (S) -> D): Resource<D> = when (this) {
    is Resource.Loading -> Resource.Loading(data?.let(mapping))
    is Resource.Success -> Resource.Success(mapping(data!!))
    is Resource.Errors -> Resource.Errors(errorInfo!!, data?.let(mapping))
}

/**
 * Get Resource from network
 *
 * @param call SuspendFunction0<T>
 * @return Resource<T>
 */
suspend fun <T> getRemoteResource(call: suspend () -> T): Resource<T> = try {
    Resource.Success(call.invoke())
} catch (e: SocketTimeoutException) {
    Resource.Errors(ErrorInfo.NetError(CODE_SOCKET_TIMEOUT, e.message!!))
} catch (e: UnknownHostException) {
    Resource.Errors(ErrorInfo.NetError(CODE_UNKNOWN_HOST, e.message!!))
} catch (e: HttpException) {
    Resource.Errors(ErrorInfo.NetError(CODE_HTTP_EXCEPTION, e.message!!))
} catch (e: IOException) {
    Resource.Errors(ErrorInfo.OtherError(CODE_UNKNOWN, e.message!!))
}

/**
 * Get Resource from local database
 *
 * @param query Function0<Flow<T>>
 * @return LiveData<Resource<T>>
 */
fun <T> getLocalResource(query: () -> Flow<T>): LiveData<Resource<T>> = try {
    query.invoke().asLiveData().map {
        if (it != null) Resource.Success(it) else Resource.Errors<T>(
            ErrorInfo.DBError("empty")
        )
    }
} catch (e: Exception) {
    MutableLiveData(Resource.Errors(ErrorInfo.DBError(e.message!!)))
}
