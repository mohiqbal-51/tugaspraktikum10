package com.iqbal.databaseroom

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Database(entities = [AppIdea::class], version = 1)
abstract class AppIdeaDatabase : RoomDatabase() {

    abstract fun appIdeaDao(): AppIdeaDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppIdeaDatabase? = null

        fun getDatabase(context: Context): AppIdeaDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppIdeaDatabase::class.java,
                    "AppIdeaDatabase"
                )
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }

        }
    }


}
