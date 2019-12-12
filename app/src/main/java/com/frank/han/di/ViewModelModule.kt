package com.frank.han.di

import androidx.lifecycle.SavedStateHandle
import com.frank.han.ui.repo.RepoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (handle: SavedStateHandle, username: String) ->
        RepoViewModel(
            handle, username, get()
        )
    }
}
