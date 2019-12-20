package com.frank.han.data.repo.entity

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
    @PrimaryKey var id: Long = 0,
    var node_id: String = "",
    var name: String = "",
    var full_name: String = "",
    var is_private: Boolean = false
) {
    fun mapVO(): RepoVO = RepoVO(name, is_private)
}
