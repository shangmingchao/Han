package com.frank.han.data.user.entity

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
    @PrimaryKey var id: Long = 0,
    var login: String = "",
    var node_id: String = ""
)
