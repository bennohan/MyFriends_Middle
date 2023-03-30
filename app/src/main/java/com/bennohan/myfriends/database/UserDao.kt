package com.bennohan.myfriends.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.crocodic.core.data.CoreDao

@Dao
interface UserDao : CoreDao<User> {

    @Query("DELETE FROM User")
    suspend fun deleteAll()

    @Query("SELECT * FROM User WHERE idRoom = 1 ")
    suspend fun getId() : User

    @Query("SELECT * FROM User WHERE idRoom = 1 ")
    fun getUser() : LiveData<User>

    @Query("SELECT EXISTS (SELECT 1 FROM User WHERE idRoom = 1)")
    suspend fun isLogin(): Boolean


}