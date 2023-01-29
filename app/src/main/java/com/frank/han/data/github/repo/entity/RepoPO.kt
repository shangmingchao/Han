package com.frank.han.data.github.repo.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
 *
 * @author frank
 * @date 2019/12/17 3:52 PM
 */
@Entity
data class RepoPO(
    @PrimaryKey val id: Long,
    val name: String,
    val is_private: Boolean,
    val owner_id: Long,
)
