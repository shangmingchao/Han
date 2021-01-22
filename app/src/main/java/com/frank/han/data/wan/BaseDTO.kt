package com.frank.han.data.wan

data class BaseDTO<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String,
)
