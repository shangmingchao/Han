package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 *
 * [P] refer to the PO object of database, [D] refer to the DTO object of network,
 * [V] refer to the VO object of UI
 * @author frank
 * @date 2019/12/9 11:06 AM
 */
fun <V, D, P> getResource(
    databaseQuery: () -> LiveData<P>,
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
