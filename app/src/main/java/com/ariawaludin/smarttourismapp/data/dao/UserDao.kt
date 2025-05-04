package com.ariawaludin.smarttourismapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ariawaludin.smarttourismapp.data.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?
}
