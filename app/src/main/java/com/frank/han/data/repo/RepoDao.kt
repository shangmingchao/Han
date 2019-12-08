package com.frank.han.data.repo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.frank.han.data.repo.entity.Repo
import com.frank.han.data.user.entity.UserWithRepos

/**
 *
 *
 * @author frank
 * @date 2019/12/7 8:18 PM
 */
@Dao
interface RepoDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveRepo(repo: List<Repo>)

    @Transaction
    @Query("SELECT * FROM user WHERE login = :username")
    suspend fun getUserRepos(username: String): UserWithRepos
}
