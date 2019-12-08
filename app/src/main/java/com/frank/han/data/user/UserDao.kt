package com.frank.han.data.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.frank.han.data.user.entity.User

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:40 PM
 */
@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUser(user: User)

    @Query("SELECT * FROM user WHERE id = :userId")
    suspend fun getUserById(userId: String): User
}
