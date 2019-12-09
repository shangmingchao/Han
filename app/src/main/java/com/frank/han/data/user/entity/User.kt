package com.frank.han.data.user.entity

import androidx.room.Entity

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:07 PM
 */
@Entity(primaryKeys = ["id", "login"])
data class User(
    val id: Long,
    val login: String
)
