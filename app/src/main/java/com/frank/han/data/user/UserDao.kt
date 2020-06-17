package com.frank.han.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frank.han.data.user.entity.UserPO
import kotlinx.coroutines.flow.Flow

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:40 PM
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: UserPO)

    @Query("SELECT * FROM UserPO WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserPO>

    @Query("SELECT * FROM UserPO WHERE login = :username")
    fun getUserByName(username: String): Flow<UserPO>
}
