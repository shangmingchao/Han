package com.frank.han.data

import android.database.sqlite.SQLiteException
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Get Single Source of Truth
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
        try {
            saveCallResult.invoke(dpMapping(remoteResource.data!!))
        } catch (e: SQLiteException) {
            // ignore
        }
    } else if (remoteResource is Resource.Errors) {
        emit(Resource.Errors(remoteResource.errorInfo!!))
        emitSource(localResource.map { it.resMapping(pvMapping) })
    }
}

/**
 * Mapping data in Resource wrapper
 */
fun <S, D> Resource<S>.resMapping(mapping: (S) -> D): Resource<D> = when (this) {
    is Resource.Loading -> Resource.Loading(data?.let(mapping))
    is Resource.Success -> Resource.Success(mapping(data!!))
    is Resource.Errors -> Resource.Errors(errorInfo!!, data?.let(mapping))
}

/**
 * Get Resource from network
 */
suspend fun <T> getRemoteResource(call: suspend () -> T): Resource<T> = try {
    Resource.Success(call.invoke())
} catch (e: SocketTimeoutException) {
    Resource.Errors(NetError(ERROR_CODE_NET_SOCKET_TIMEOUT, e))
} catch (e: UnknownHostException) {
    Resource.Errors(NetError(ERROR_CODE_NET_UNKNOWN_HOST, e))
} catch (e: HttpException) {
    Resource.Errors(NetError(ERROR_CODE_NET_HTTP_EXCEPTION, e))
} catch (e: IOException) {
    Resource.Errors(NetError(ERROR_CODE_COMMON, e))
}

/**
 * Get Resource from database
 *
 * Note: Flow will not work if databaseQuery throws an exception. So the further saveCallResult will not be signaled.
 * It's a bug?!
 */
fun <T> getLocalResource(query: () -> Flow<T>): LiveData<Resource<T>> = query.invoke()
    .map { Pair<T?, Throwable?>(it, null) }
    .catch { e -> emit(Pair<T?, Throwable?>(null, e)) }
    .asLiveData()
    .map {
        it.first?.let { data ->
            return@map Resource.Success(data)
        }
        it.second?.let { e ->
            return@map Resource.Errors(DBError(ERROR_CODE_COMMON, e))
        }
        return@map Resource.Errors(DBError(ERROR_CODE_DB_NO_DATA, null))
    }
