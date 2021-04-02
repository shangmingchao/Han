package com.frank.han.widget

import com.frank.han.data.Resource

/**
 * Base View
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
interface BaseView {

    /**
     * Set resource
     *
     * @param resource Resource
     */
    fun <T> bindResource(resource: Resource<T>)
}
