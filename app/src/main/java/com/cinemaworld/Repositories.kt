package com.cinemaworld

import android.content.Context
import androidx.room.Room
import com.cinemaworld.model.AppDatabase
import com.cinemaworld.model.users.repositories.UsersRepository
import com.cinemaworld.model.users.repositories.room.RoomUsersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Contains singleton dependencies.
 */
object Repositories {

    private lateinit var applicationContext: Context

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "database.db")
            .createFromAsset("initial_database.db")
            .build()
    }

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    // ---

    val usersRepository: UsersRepository by lazy {
        RoomUsersRepository(ioDispatcher, database.getUsersDao())
    }

    /**
     * Call this method in all application components that may be created at app startup/restoring
     * (e.g. in onCreate of activities and services)
     */
    fun init(context: Context) {
        applicationContext = context
    }

}