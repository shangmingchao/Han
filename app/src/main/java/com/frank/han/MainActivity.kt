package com.frank.han

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.frank.han.util.hideSystemUI
import kotlinx.android.synthetic.main.activity_main.*

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
        window.hideSystemUI()
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_main)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
