package com.frank.han.data.repo.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 *
 *
 * @author frank
 * @date 2019/12/2 6:34 PM
 */
@Entity(indices = [Index(value = ["id"], unique = true)])
data class Repo(
    @PrimaryKey val id: Long,
    val full_name: String,
    val description: String,
    val owner_login: String
)
