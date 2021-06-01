package com.frank.han.util

import android.view.LayoutInflater
import android.view.View
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import com.frank.han.data.DBError
import com.frank.han.data.Error
import com.frank.han.data.NetError
import com.frank.han.data.OtherError
import com.frank.han.data.Resource
import com.frank.han.data.onDatabaseError
import com.frank.han.data.onNetworkError
import com.frank.han.data.onUnknownError
import com.frank.han.widget.HResourceView
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

/**
 * Render data in fragment by common logic(UI state in loading, normal, error)
 */
inline fun <reified VO, reified VB : ViewBinding> Fragment.renderPage(
    data: LiveData<Resource<VO>>,
    viewBinding: VB,
    noinline dataBinding: (VB, VO) -> Unit
) {
    data.observe(viewLifecycleOwner) { res ->
        val resourceView = viewBinding.root as? HResourceView ?: return@observe
        resourceView.dataBinding(res) { vo -> dataBinding(viewBinding, vo) }
        if (res is Error) {
            when (res.errorInfo) {
                is DBError -> onDatabaseError(res.errorInfo)
                is NetError -> onNetworkError(res.errorInfo)
                is OtherError -> onUnknownError(res.errorInfo)
            }
        }
    }
}
