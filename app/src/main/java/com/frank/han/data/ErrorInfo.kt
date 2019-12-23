package com.frank.han.data

/**
 *
 *
 * @author frank
 * @date 2019/12/23 10:11 AM
 */
const val CODE_UNKNOWN = 0
const val CODE_SOCKET_TIMEOUT = 1
const val CODE_UNKNOWN_HOST = 2
const val CODE_DB_ERROR = 3
sealed class ErrorInfo(
    val code: Int,
    val msg: String
) {
    class NetError(code: Int, msg: String) : ErrorInfo(code, msg)
    class DBError(msg: String) : ErrorInfo(CODE_DB_ERROR, msg)
    class OtherError(code: Int, msg: String) : ErrorInfo(code, msg)
}
