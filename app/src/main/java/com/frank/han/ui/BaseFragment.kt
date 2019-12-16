package com.frank.han.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 *
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
abstract class BaseFragment : Fragment() {

    abstract val layoutId: Int?
    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutId?.let {
            root = inflater.inflate(it, container, false)
            root
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        root = null
    }
}
