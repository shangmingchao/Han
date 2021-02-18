package com.frank.han.data.github.repo.entity

import com.frank.han.data.github.user.entity.UserDTO
import com.google.gson.annotations.SerializedName

/**
 * RepoDTO
 *
 * @author frank
 * @date 2019/12/17 3:52 PM
 */
data class RepoDTO(
    val id: Long,
    val name: String,
    @SerializedName("private")
    val is_private: Boolean,
    val owner: UserDTO,
)
