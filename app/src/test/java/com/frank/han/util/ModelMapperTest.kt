package com.frank.han.util

import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * ModelMapper Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
class ModelMapperTest {

    /**
     * testRepoDTO2PO
     */
    @Test
    fun testRepoDTO2PO() {
        val repoDTO = mockRepoDTO
        val repoPO = ModelMapper.map(repoDTO)
        assertThat(repoPO.name).isEqualTo(repoDTO.name)
    }

    /**
     * testRepoPO2VO
     */
    @Test
    fun testRepoPO2VO() {
        val repoPO = mockRepoPO
        val repoVO = ModelMapper.map(repoPO)
        assertThat(repoVO.desc).isEqualTo(repoPO.name)
        assertThat(repoVO.isPrivate).isFalse()
    }

    /**
     * testUserDTO2PO
     */
    @Test
    fun testUserDTO2PO() {
        val userDTO = mockUserDTO
        val userPO = ModelMapper.map(userDTO)
        assertThat(userPO.name).isEqualTo(userDTO.name)
    }

    /**
     * testUserPO2VO
     */
    @Test
    fun testUserPO2VO() {
        val userPO = mockUserPO
        val userVO = ModelMapper.map(userPO)
        assertThat(userVO.username).isEqualTo(userPO.name)
    }
}
