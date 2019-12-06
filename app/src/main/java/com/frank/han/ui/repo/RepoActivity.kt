package com.frank.han.ui.repo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.frank.han.R
import com.frank.han.databinding.ActivityRepoBinding

/**
 *
 *
 * @author frank
 * @date 2019/12/3 10:31 AM
 */
class RepoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRepoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRepoBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState != null) {
            return
        }
        supportFragmentManager.findFragmentById(R.id.container) ?: supportFragmentManager.commit {
            add(R.id.container, RepoFragment())
        }
    }
}
