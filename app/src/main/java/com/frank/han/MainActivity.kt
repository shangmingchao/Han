package com.frank.han

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.frank.han.databinding.ActivityMainBinding
import com.frank.han.ui.repo.RepoActivity

/**
 * Main Activity
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        binding.repo.setOnClickListener { startActivity(Intent(this, RepoActivity::class.java)) }
    }
}
