package com.frank.han.data.repo

import com.frank.han.data.repo.entity.RepoPO
import com.frank.han.data.repo.entity.RepoVO
import com.frank.han.util.ModelMapper
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 *
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
class MapperTest {

    @Test
    fun mapValidate() {
        val po: RepoPO = RepoPO(1L, "name1", false, 1L)
        val vo: RepoVO = ModelMapper.map(po)
        assertEquals(vo.desc, "name1")
    }
}
