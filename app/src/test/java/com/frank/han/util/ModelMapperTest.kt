package com.frank.han.util

import com.frank.han.data.repo.entity.LicenseDTO
import com.frank.han.data.repo.entity.RepoDTO
import com.frank.han.data.repo.entity.RepoPO
import com.frank.han.data.repo.entity.RepoVO
import com.frank.han.data.user.entity.UserDTO
import com.frank.han.data.user.entity.UserPO
import com.frank.han.data.user.entity.UserVO
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 *
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
class ModelMapperTest {

    @Test
    fun testRepoDTO2PO() {
        val userDTO = UserDTO(
            "", 1L, "", "", "", "", "",
            "", "", "", "", "", "", "",
            "", "", "", false, "", "", "",
            "", "", false, "", 0, 0, 0,
            0, "", ""
        )
        val licenseDTO = LicenseDTO("", "", "", "", "")
        val repoDTO = RepoDTO(
            2L, "", "name1", "", false,
            userDTO, "", "", false, "", "", "",
            "", "", "", "", "", "", "",
            "", "", "", "", "", "", "",
            "", "", "", "", "", "", "",
            "", "", "", "", "", "", "",
            "", "", "", "", "", "", "",
            "", "", "", "", "", "", "",
            0, 0, 0, false, false, false, false,
            false, 0, false, false, 0, 0, 0,
            0, "", "", licenseDTO, ""
        )
        val repoPO = ModelMapper.map(repoDTO)
        assertThat(repoPO).isEqualTo(RepoPO(2L, "name1", false, 1L))
    }

    @Test
    fun testRepoPO2VO() {
        val repoPO = RepoPO(2L, "name1", false, 1L)
        val repoVO = ModelMapper.map(repoPO)
        assertThat(repoVO).isEqualTo(RepoVO("name1", false))
    }

    @Test
    fun testUserDTO2PO() {
        val userDTO = UserDTO(
            "login1", 1L, "", "", "",
            "", "", "", "", "",
            "", "", "", "", "",
            "", "", false, "name1", "",
            "", "", "", false, "",
            0, 0, 0, 0, "", ""
        )
        val userPO = ModelMapper.map(userDTO)
        assertThat(userPO).isEqualTo(UserPO(1L, "login1", "name1"))
    }

    @Test
    fun testUserPO2VO() {
        val userPO = UserPO(1L, "login1", "name1")
        val userVO = ModelMapper.map(userPO)
        assertThat(userVO).isEqualTo(UserVO("name1"))
    }
}
