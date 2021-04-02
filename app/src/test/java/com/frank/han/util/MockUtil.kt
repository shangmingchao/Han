package com.frank.han.util

import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.entity.ArticleDTO
import com.frank.han.data.wan.wechat.entity.ArticlesDTO

/**
 * MOCK_USER_ID
 */
const val MOCK_USER_ID = 1L

/**
 * MOCK_USER_LOGIN
 */
const val MOCK_USER_LOGIN = "login1"

/**
 * MOCK_USER_NAME
 */
const val MOCK_USER_NAME = "name1"

/**
 * MOCK_USER_PUBLIC_REPOS
 */
const val MOCK_USER_PUBLIC_REPOS = 128

/**
 * MOCK_REPO_ID
 */
const val MOCK_REPO_ID = 2L

/**
 * MOCK_REPO_NAME
 */
const val MOCK_REPO_NAME = "repo1"

/**
 * MOCK_ARTICLE_AUTHOR
 */
const val MOCK_ARTICLE_AUTHOR = "author1"

/**
 * MOCK_ARTICLE_TITLE
 */
const val MOCK_ARTICLE_TITLE = "title1"

/**
 * mockUserDTO
 */
val mockUserDTO = UserDTO(MOCK_USER_ID, MOCK_USER_LOGIN, MOCK_USER_NAME, MOCK_USER_PUBLIC_REPOS)

/**
 * mockUserPO
 */
val mockUserPO = UserPO(MOCK_USER_ID, MOCK_USER_LOGIN, MOCK_USER_NAME, MOCK_USER_PUBLIC_REPOS)

/**
 * mockRepoDTO
 */
val mockRepoDTO = RepoDTO(MOCK_REPO_ID, MOCK_REPO_NAME, false, mockUserDTO)

/**
 * mockRepoPO
 */
val mockRepoPO = RepoPO(MOCK_REPO_ID, MOCK_REPO_NAME, false, MOCK_USER_ID)

/**
 * mockArticle
 */
val mockArticle = ArticleDTO(MOCK_ARTICLE_AUTHOR, MOCK_ARTICLE_TITLE)

/**
 * mockArticles
 */
val mockArticles = BaseDTO(
    ArticlesDTO(
        0, listOf(mockArticle), 0,
        false, 0, 0, 0
    ),
    0,
    "",
)
