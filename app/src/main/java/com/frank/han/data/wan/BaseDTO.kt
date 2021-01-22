package com.frank.han.data.wan

/**
 * BaseDTO
 *
 * @author shangmingchao
 */
data class BaseDTO<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String,
)
