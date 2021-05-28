package com.frank.han.widget

import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.data.ERROR_CODE_NET_UNKNOWN_HOST
import com.frank.han.data.Error
import com.frank.han.data.Loading
import com.frank.han.data.NetError
import com.frank.han.data.Success
import com.frank.han.ui.main.MainFragment
import com.google.common.truth.Truth.assertThat
import java.net.UnknownHostException
import org.junit.Test
import org.junit.runner.RunWith

/**
 * HResourceView UI test
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
@RunWith(AndroidJUnit4::class)
class HResourceViewTest {

    @Test
    fun dataBinding() {
        val scenario = launchFragmentInContainer<MainFragment>()
        scenario.onFragment {
            val container = it.view as? HResourceView ?: return@onFragment
            container.dataBinding(Loading("mockLoading")) {}
            assertThat(container.visibility).isEqualTo(INVISIBLE)
            container.dataBinding(Success("mockSuccess")) {}
            assertThat(container.visibility).isEqualTo(VISIBLE)
            container.dataBinding(
                Error(
                    NetError(
                        ERROR_CODE_NET_UNKNOWN_HOST,
                        UnknownHostException("")
                    ), "mockError"
                )
            ) {}
            assertThat(container.visibility).isEqualTo(GONE)
        }
    }
}
