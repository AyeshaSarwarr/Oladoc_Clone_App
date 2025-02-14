package com.example.oladoc.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.oladoc.database.dao.UserProfileDao
import com.example.oladoc.database.UserProfile

@Database(entities = [UserProfile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
}
