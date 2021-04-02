package com.frank.han.data.github.user.entity

/**
 * UserDTO
 *
 * @author frank
 * @date 2019/12/18 2:37 PM
 */
data class UserDTO(

    /**
     * id
     */
    val id: Long,

    /**
     * login name
     */
    val login: String,

    /**
     * nickname
     */
    val name: String,

    /**
     * The count of public repositories
     */
    val public_repos: Int,
)
