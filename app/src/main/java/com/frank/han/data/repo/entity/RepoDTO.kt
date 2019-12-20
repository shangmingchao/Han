package com.frank.han.data.repo.entity

import com.google.gson.annotations.SerializedName

/**
 *
 *
 * @author frank
 * @date 2019/12/17 3:52 PM
 */
data class RepoDTO(
    var id: Long,
    var node_id: String,
    var name: String,
    var full_name: String,
    @SerializedName("private")
    var is_private: Boolean
) {
    fun mapPO(): RepoPO = RepoPO(id, node_id, name, full_name, is_private)
}
