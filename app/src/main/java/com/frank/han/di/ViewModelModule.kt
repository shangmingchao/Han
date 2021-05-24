package com.frank.han.di

import com.frank.han.ui.article.ArticleViewModel
import com.frank.han.ui.repo.RepoViewModel
import com.frank.han.ui.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (username: String) ->
        RepoViewModel(
            get(), get(), username, get()
        )
    }

    viewModel { (username: String) ->
        UserViewModel(
            get(), get(), username, get()
        )
    }

    viewModel { (id: String, page: Int) ->
        ArticleViewModel(
            get(), get(), id, page, get()
        )
    }
}
