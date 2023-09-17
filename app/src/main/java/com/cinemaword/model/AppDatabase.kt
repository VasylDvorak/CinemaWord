package com.cinemaword.model

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cinemaword.model.users.repositories.room.UserDbEntity
import com.cinemaword.model.users.repositories.room.UsersDao

@Database(
    version = 1,
    entities = [
        UserDbEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUsersDao(): UsersDao

}
