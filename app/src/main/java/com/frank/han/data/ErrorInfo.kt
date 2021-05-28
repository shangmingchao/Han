package com.frank.han.data

/**
 * The normal error
 *
 * @author frank
 * @date 2019/12/23 10:11 AM
 */
sealed class ErrorInfo

/**
 * Network Error
 *
 * @constructor creates NetError
 */
data class NetError(val code: Int?, val e: Throwable?) : ErrorInfo()

/**
 * Database Error
 *
 * @constructor creates DBError
 */
data class DBError(val code: Int?, val e: Throwable?) : ErrorInfo()

/**
 * Other Error
 *
 * @constructor creates OtherError
 */
data class OtherError(val code: Int?, val e: Throwable?) : ErrorInfo()

/**
 * Common exception
 */
const val ERROR_CODE_COMMON = 0

/**
 * No data in database
 */
const val ERROR_CODE_DB_NO_DATA = 100

/**
 * Http status not 200 OK
 */
const val ERROR_CODE_NET_HTTP_EXCEPTION = 200

/**
 * SocketTimeoutException
 */
const val ERROR_CODE_NET_SOCKET_TIMEOUT = 201

/**
 * UnknownHostException
 */
const val ERROR_CODE_NET_UNKNOWN_HOST = 202

/**
 * JsonParseException
 */
const val ERROR_CODE_JSON_PARSE_EXCEPTION = 203
