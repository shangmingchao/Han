package com.frank.han.data.user.entity

import androidx.room.Entity
import androidx.room.Index

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:07 PM
 */
@Entity(
    primaryKeys = ["id", "login"],
    indices = [Index(value = ["id"], unique = true)]
)
data class User(
    val id: Long,
    val login: String
)
