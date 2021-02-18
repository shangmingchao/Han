package com.frank.han.data.wan

/**
 * BaseDTO
 *
 * @author frank
 * @date 2019/12/8 7:47 PM
 */
data class BaseDTO<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String,
)
