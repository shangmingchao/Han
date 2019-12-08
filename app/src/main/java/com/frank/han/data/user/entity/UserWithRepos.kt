package com.frank.han.data.user.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.frank.han.data.repo.entity.Repo

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:28 PM
 */
data class UserWithRepos(
    @Embedded val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "owner_login"
    )
    val repos: List<Repo>
)
