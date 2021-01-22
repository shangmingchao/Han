package com.frank.han.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.frank.han.R
import com.frank.han.data.Resource

/**
 * Custom TextView
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
open class HTextView : AppCompatTextView, BaseView<CharSequence> {

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

    override fun setResource(resource: Resource<CharSequence>) {
        text = when (resource) {
            is Resource.Loading -> resources.getString(R.string.loading)
            is Resource.Success -> resource.data
            is Resource.Errors -> resources.getString(R.string.empty_text)
        }
    }
}
