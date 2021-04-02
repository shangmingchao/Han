package com.frank.han.data.github.user.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * UserPO
 *
 * @author frank
 * @date 2019/12/18 2:37 PM
 */
@Entity
data class UserPO(

    /**
     * id
     */
    @PrimaryKey val id: Long,

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
