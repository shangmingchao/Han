package com.frank.han

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.frank.han.databinding.ActivityMainBinding
import com.frank.han.util.hideSystemUI

/**
 * Main Activity
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)
        val navController = findNavController(R.id.nav_host_main)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        viewBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
