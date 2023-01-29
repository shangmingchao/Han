package com.frank.han.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.frank.han.data.Resource

/**
 * Custom TextView
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
open class HTextView : AppCompatTextView, BaseView {

    constructor(context: Context, attrs: AttributeSet?) : this(
        context,
        attrs,
        android.R.attr.textViewStyle,
    )

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr,
    )

    override fun <T> bindResource(resource: Resource<T>) {
        // do nothing
    }
}
