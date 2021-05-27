package com.frank.han.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.frank.han.R
import com.frank.han.data.DBError
import com.frank.han.data.Error
import com.frank.han.data.Loading
import com.frank.han.data.NetError
import com.frank.han.data.OtherError
import com.frank.han.data.Resource
import com.frank.han.data.Success

/**
 * HResourceView
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
class HResourceView : ConstraintLayout, BaseView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        0,
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun <T> bindResource(resource: Resource<T>) {
        visibility = when (resource) {
            is Loading -> {
                INVISIBLE
            }
            is Success -> {
                VISIBLE
            }
            is Error -> {
                val tips = when (resource.errorInfo) {
                    is DBError -> getDatabaseErrorMsg(resource.errorInfo)
                    is NetError -> getNetworkErrorMsg(resource.errorInfo)
                    is OtherError -> getUnknownErrorMsg(resource.errorInfo)
                }
                Toast.makeText(context, tips, Toast.LENGTH_SHORT).show()
                GONE
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> dataBinding(resource: Resource<T>, successRun: (T) -> Unit) {
        bindResource(resource)
        if (resource is Success) {
            successRun(resource.data)
        }
    }

    private fun getDatabaseErrorMsg(errorInfo: DBError): String {
        return resources.getString(R.string.database_error, errorInfo.code)
    }

    private fun getNetworkErrorMsg(errorInfo: NetError): String {
        return resources.getString(R.string.network_error, errorInfo.code)
    }

    private fun getUnknownErrorMsg(errorInfo: OtherError): String {
        return resources.getString(R.string.unknown_error, errorInfo.code)
    }
}
