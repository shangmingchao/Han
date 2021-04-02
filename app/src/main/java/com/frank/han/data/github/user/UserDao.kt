package com.frank.han.data.github.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frank.han.data.github.user.entity.UserPO
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:40 PM
 */
@Dao
interface UserDao {

    /**
     * Save user
     *
     * @param user UserPO
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserPO)

    /**
     * Get user by user's id
     *
     * @param userId String
     * @return Flow<UserPO>
     */
    @Query("SELECT * FROM UserPO WHERE login = :userId")
    fun getUserById(userId: String): Flow<UserPO>

    /**
     * Get user by user's username
     *
     * @param username String
     * @return Flow<UserPO>
     */
    @Query("SELECT * FROM UserPO WHERE name = :username")
    fun getUserByName(username: String): Flow<UserPO>
}
