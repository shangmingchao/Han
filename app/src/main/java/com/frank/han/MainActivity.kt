package com.frank.han

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.frank.han.databinding.ActivityMainBinding
import com.frank.han.util.binding

/**
 * Main Activity
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
class MainActivity : AppCompatActivity() {

    private val viewBinding by binding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        setSupportActionBar(viewBinding.toolbar)
        val navController = findNavController(R.id.nav_host_main)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        viewBinding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
