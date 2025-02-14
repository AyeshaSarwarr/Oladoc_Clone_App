package com.example.oladoc.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.oladoc.database.UserProfile

@Dao
interface UserProfileDao {
    @Insert
    suspend fun insert(userProfile: UserProfile)

    @Query("SELECT * FROM user_profiles")
    fun getAllProfiles(): List<UserProfile>
}
