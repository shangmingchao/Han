package com.frank.han.util

import com.frank.han.data.github.repo.entity.LicenseDTO
import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.entity.ArticleDTO
import com.frank.han.data.wan.wechat.entity.ArticlesDTO

fun mockUser() = UserDTO(
    "login1", 1L, "", "", "", "", "",
    "", "", "", "", "", "", "",
    "", "", "", false, "name1", "", "",
    "", "", false, "", 0, 0, 0,
    0, "", ""
)

fun mockRepo() = RepoDTO(
    2L, "", "name1", "", false,
    mockUser(), "", "", false, "", "", "",
    "", "", "", "", "", "", "",
    "", "", "", "", "", "", "",
    "", "", "", "", "", "", "",
    "", "", "", "", "", "", "",
    "", "", "", "", "", "", "",
    "", "", "", "", "", "", "",
    0, 0, 0, false, false, false, false,
    false, 0, false, false, 0, 0, 0,
    0, "", "", mockLicense(), ""
)

fun mockLicense() = LicenseDTO("", "", "", "", "")

fun mockArticle() = ArticleDTO(
    "", 1, "", false, 1,
    "", false, 1, "", "",
    "", false, "", 1, "",
    "", "", "", "", "",
    1, 1, 1, 1, "",
    1, "", emptyList(), "title1", 1,
    1, 1, 1
)

fun mockArticles(): BaseDTO<ArticlesDTO> {
    val articles = listOf(mockArticle())
    val articlesDTO = ArticlesDTO(1, articles, 1, false, 1, 1, 1)
    return BaseDTO(articlesDTO, 0, "")
}