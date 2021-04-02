package com.frank.han.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.frank.han.data.Error
import com.frank.han.data.Loading
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
                Toast.makeText(context, "${resource.errorInfo}", Toast.LENGTH_SHORT).show()
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
}
