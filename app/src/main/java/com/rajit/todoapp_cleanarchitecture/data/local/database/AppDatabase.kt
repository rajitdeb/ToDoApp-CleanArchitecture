package com.rajit.todoapp_cleanarchitecture.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rajit.todoapp_cleanarchitecture.data.local.dao.TaskDao
import com.rajit.todoapp_cleanarchitecture.domain.entity.Task

@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "TaskDatabase"
            ).build()
    }

}