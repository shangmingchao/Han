package com.frank.han.widget

import android.content.Context
import android.util.AttributeSet

/**
 * Custom Button
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
class HButton : HTextView {

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
}
