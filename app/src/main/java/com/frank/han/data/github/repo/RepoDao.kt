package com.frank.han.data.github.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.user.entity.UserWithRepos
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author frank
 * @date 2019/12/7 8:18 PM
 */
@Dao
interface RepoDao {

    /**
     * save Repo
     *
     * @param repo List<RepoPO>
     */
    @Insert(onConflict = REPLACE)
    suspend fun saveRepo(repo: List<RepoPO>)

    /**
     * Get user's repos by username
     * @param username String
     * @return Flow<UserWithRepos>
     */
    @Transaction
    @Query("SELECT * FROM UserPO WHERE login = :username")
    fun getUserRepos(username: String): Flow<UserWithRepos>
}
