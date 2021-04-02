package com.frank.han.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.frank.han.R
import com.frank.han.data.Error
import com.frank.han.data.Loading
import com.frank.han.data.Resource
import com.frank.han.data.Success

/**
 * Custom TextView
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
open class HTextView : AppCompatTextView, BaseView {

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        android.R.attr.textViewStyle
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun <T> bindResource(resource: Resource<T>) {
        text = when (resource) {
            is Loading -> resources.getString(R.string.loading)
            is Success -> resource.data as CharSequence
            is Error -> resources.getString(R.string.data_error)
        }
    }
}
