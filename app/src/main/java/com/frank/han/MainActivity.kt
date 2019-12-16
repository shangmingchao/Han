package com.frank.han

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frank.han.util.lightStatusBar

/**
 * Main Activity
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.lightStatusBar()
    }
}
