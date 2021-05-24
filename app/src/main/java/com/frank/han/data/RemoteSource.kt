package com.frank.han.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineDispatcher

/**
 * Get data source from remote
 *
 * @param networkCall network call
 * @return LiveData<Resource<T>>
 */
fun <T> getNetResource(
    dispatcher: CoroutineDispatcher,
    networkCall: suspend () -> T,
): LiveData<Resource<T>> = liveData(dispatcher, 0) {
    emit(Loading())
    emit(getRemoteResource(networkCall))
}
