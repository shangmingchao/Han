package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

/**
 *
 *
 * @author frank
 * @date 2019/12/9 11:06 AM
 */
fun <T, R> getResource(
    databaseQuery: () -> LiveData<T>,
    networkCall: suspend () -> R,
    saveCallResult: suspend (R) -> Unit
): LiveData<Resource<T>> = liveData(Dispatchers.IO) {
    emit(Resource.Loading())
    val localLiveData = getLocalResource(databaseQuery)
    localLiveData?.let { emitSource(it) }
    val remoteResource = getRemoteResource(networkCall)
    if (remoteResource is Resource.Success) {
        saveCallResult.invoke(remoteResource.data!!)
    } else if (remoteResource is Resource.Errors) {
        emit(Resource.Errors(remoteResource.message!!))
        localLiveData?.let { emitSource(it) }
    }
}
