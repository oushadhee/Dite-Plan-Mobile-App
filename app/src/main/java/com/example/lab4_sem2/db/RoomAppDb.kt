package com.example.lab4_sem2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserEntity::class], version = 2)
abstract class RoomAppDb : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: RoomAppDb? = null

        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE UserEntity ADD COLUMN phone TEXT DEFAULT ''")
            }
        }


        fun getAppDatabase(context: Context): RoomAppDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomAppDb::class.java,
                    "AppDB" // Correct the database name here
                )
                    .addMigrations(migration_1_2)
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}
