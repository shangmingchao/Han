package com.frank.han.util

import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.entity.ArticleDTO
import com.frank.han.data.wan.wechat.entity.ArticlesDTO

/**
 * MOCKED_USER_ID
 */
const val MOCKED_USER_ID = 1L

/**
 * MOCKED_USER_LOGIN
 */
const val MOCKED_USER_LOGIN = "login1"

/**
 * MOCKED_USER_NAME
 */
const val MOCKED_USER_NAME = "name1"

/**
 * MOCKED_USER_PUBLIC_REPOS
 */
const val MOCKED_USER_PUBLIC_REPOS = 128

/**
 * MOCKED_REPO_ID
 */
const val MOCKED_REPO_ID = 2L

/**
 * MOCKED_REPO_NAME
 */
const val MOCKED_REPO_NAME = "repo1"

/**
 * MOCKED_ARTICLE_AUTHOR
 */
const val MOCKED_ARTICLE_AUTHOR = "author1"

/**
 * MOCKED_ARTICLE_TITLE
 */
const val MOCKED_ARTICLE_TITLE = "title1"

/**
 * mockedUserDTO
 */
val mockedUserDTO =
    UserDTO(MOCKED_USER_ID, MOCKED_USER_LOGIN, MOCKED_USER_NAME, MOCKED_USER_PUBLIC_REPOS)

/**
 * mockedUserPO
 */
val mockedUserPO =
    UserPO(MOCKED_USER_ID, MOCKED_USER_LOGIN, MOCKED_USER_NAME, MOCKED_USER_PUBLIC_REPOS)

/**
 * mockedRepoDTO
 */
val mockedRepoDTO = RepoDTO(MOCKED_REPO_ID, MOCKED_REPO_NAME, false, mockedUserDTO)

/**
 * mockedRepoPO
 */
val mockedRepoPO = RepoPO(MOCKED_REPO_ID, MOCKED_REPO_NAME, false, MOCKED_USER_ID)

/**
 * mockedArticle
 */
val mockedArticle = ArticleDTO(MOCKED_ARTICLE_AUTHOR, MOCKED_ARTICLE_TITLE)

/**
 * mockedArticles
 */
val mockedArticles = BaseDTO(
    ArticlesDTO(
        0, listOf(mockedArticle), 0,
        false, 0, 0, 0
    ),
    0,
    "",
)
