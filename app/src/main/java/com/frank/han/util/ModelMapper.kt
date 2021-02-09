package com.frank.han.util

import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.repo.entity.RepoVO
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.data.github.user.entity.UserVO

/**
 * ModelMapper. Should be replaced
 *
 * @author frank
 * @date 2019/12/20 4:07 PM
 */
@Deprecated(
    "Should be replaced in feature"
)
object ModelMapper {

    fun map(dto: RepoDTO): RepoPO {
        return RepoPO(dto.id, dto.name, dto.is_private, dto.owner.id)
    }

    fun map(po: RepoPO): RepoVO {
        return RepoVO(po.name, po.is_private)
    }

    fun map(dto: UserDTO): UserPO {
        return UserPO(dto.id, dto.login, dto.name)
    }

    fun map(po: UserPO): UserVO {
        return UserVO(po.name)
    }
}
