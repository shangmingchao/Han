package com.frank.han.util

import com.frank.han.data.repo.entity.RepoDTO
import com.frank.han.data.repo.entity.RepoPO
import com.frank.han.data.repo.entity.RepoVO
import com.frank.han.data.user.entity.UserDTO
import com.frank.han.data.user.entity.UserPO
import com.frank.han.data.user.entity.UserVO

/**
 *
 *
 * @author frank
 * @date 2019/12/20 4:07 PM
 */
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
