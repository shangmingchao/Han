package com.frank.han.util

import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.user.entity.UserPO
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * ModelMapper Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
class ModelMapperTest {

    @Test
    fun testRepoDTO2PO() {
        val repoDTO = mockRepo()
        val repoPO = ModelMapper.map(repoDTO)
        assertThat(repoPO).isEqualTo(RepoPO(2L, "name1", false, 1L))
    }

    @Test
    fun testRepoPO2VO() {
        val repoPO = RepoPO(2L, "name1", false, 1L)
        val repoVO = ModelMapper.map(repoPO)
        assertThat(repoVO.desc).isEqualTo("name1")
        assertThat(repoVO.isPrivate).isFalse()
    }

    @Test
    fun testUserDTO2PO() {
        val userDTO = mockUser()
        val userPO = ModelMapper.map(userDTO)
        assertThat(userPO).isEqualTo(UserPO(1L, "login1", "name1"))
    }

    @Test
    fun testUserPO2VO() {
        val userPO = UserPO(1L, "login1", "name1")
        val userVO = ModelMapper.map(userPO)
        assertThat(userVO.username).isEqualTo("name1")
    }
}
