package com.frank.han.data

/**
 * The normal error
 *
 * @author frank
 * @date 2019/12/23 10:11 AM
 */
sealed class ErrorInfo(
    val code: Int,
    val msg: String
) {
    /**
     * Network Error
     *
     * @constructor creates NetError
     */
    class NetError(code: Int, msg: String) : ErrorInfo(code, msg)

    /**
     * Database Error
     *
     * @constructor creates DBError
     */
    class DBError(msg: String) : ErrorInfo(CODE_DB_ERROR, msg)

    /**
     * Other Error
     *
     * @constructor creates OtherError
     */
    class OtherError(code: Int, msg: String) : ErrorInfo(code, msg)
}
