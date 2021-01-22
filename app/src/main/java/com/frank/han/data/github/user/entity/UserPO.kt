package com.frank.han.data.github.user.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *
 * @author frank
 * @date 2019/12/18 2:37 PM
 */
@Entity
data class UserPO(
    @PrimaryKey val id: Long,
    val login: String,
    val name: String
)
