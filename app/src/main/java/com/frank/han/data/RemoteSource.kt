package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers

/**
 * Get data source from remote
 *
 * @param networkCall network call
 * @return LiveData<Resource<T>>
 */
fun <T> getNetResource(
    networkCall: suspend () -> T
): LiveData<Resource<T>> = liveData(Dispatchers.IO, 0) {
    emit(Resource.Loading())
    emit(getRemoteResource(networkCall))
}
