package com.frank.han.data.github.user.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.frank.han.data.github.repo.entity.RepoPO

/**
 *
 *
 * @author frank
 * @date 2019/12/20 6:38 PM
 */
data class UserWithRepos(
    @Embedded val user: UserPO,
    @Relation(
        parentColumn = "id",
        entityColumn = "owner_id"
    )
    val repos: List<RepoPO>
)
