package com.frank.han.util

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.frank.han.data.Resource
import com.frank.han.widget.HResourceView
import com.google.android.material.tabs.TabLayout
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * ViewBinding Extensions
 *
 * @author frank
 * @date 2019/12/16 3:51 PM
 */
fun <VB : ViewBinding> ComponentActivity.binding(inflate: (LayoutInflater) -> VB) = lazy {
    inflate(layoutInflater).also {
        setContentView(it.root)
    }
}

fun <VB : ViewBinding> Fragment.binding(bind: (View) -> VB) =
    FragmentBindingDelegate(bind)

fun <VB : ViewBinding> Dialog.binding(inflate: (LayoutInflater) -> VB) = lazy {
    inflate(layoutInflater).also { setContentView(it.root) }
}

fun <VB : ViewBinding> ViewGroup.binding(
    inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    attachToParent: Boolean = true
) = lazy {
    inflate(LayoutInflater.from(context), if (attachToParent) this else null, attachToParent)
}

fun <VB : ViewBinding> TabLayout.Tab.setCustomView(
    inflate: (LayoutInflater) -> VB,
    onBindView: VB.() -> Unit
) {
    customView = inflate(LayoutInflater.from(parent!!.context)).apply(onBindView).root
}

inline fun <reified VB : ViewBinding> TabLayout.Tab.bindCustomView(
    bind: (View) -> VB,
    onBindView: VB.() -> Unit
) =
    customView?.let { bind(it).run(onBindView) }

class FragmentBindingDelegate<VB : ViewBinding>(
    private val bind: (View) -> VB
) : ReadOnlyProperty<Fragment, VB> {

    private var binding: VB? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): VB {
        if (binding == null) {
            binding = bind(thisRef.requireView())
            thisRef.viewLifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestroyView() {
                    binding = null
                }
            })
        }
        return binding!!
    }
}

inline fun <reified VO, reified VB : ViewBinding> Fragment.bindData(
    data: LiveData<Resource<VO>>,
    view: VB,
    noinline binding: (VO, VB) -> Unit
) {
    data.observe(viewLifecycleOwner) { res ->
        val resourceView = view.root as? HResourceView ?: return@observe
        resourceView.dataBinding(res) { vo -> binding(vo, view) }
    }
}
