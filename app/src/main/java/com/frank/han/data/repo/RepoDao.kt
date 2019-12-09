package com.frank.han.data.repo

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.frank.han.data.repo.entity.Repo

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

    @Query("SELECT * FROM repo")
    fun getUserRepos(): LiveData<List<Repo>>
}
