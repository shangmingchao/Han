package com.frank.han.data.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.frank.han.data.repo.entity.RepoPO
import com.frank.han.data.user.entity.UserWithRepos
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author frank
 * @date 2019/12/7 8:18 PM
 */
@Dao
interface RepoDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveRepo(repo: List<RepoPO>)

    @Transaction
    @Query("SELECT * FROM UserPO WHERE login = :username")
    fun getUserRepos(username: String): Flow<UserWithRepos>
}
