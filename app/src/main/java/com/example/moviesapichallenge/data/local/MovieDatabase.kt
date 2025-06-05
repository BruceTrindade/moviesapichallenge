package com.example.moviesapichallenge.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities =  [MovieLocal::class],
    version = 1,
    exportSchema = false
)

abstract class MovieDatabase: RoomDatabase() {

    abstract fun favoriteMovieDao(): MovieDao

    companion object {
        const val DATABASE_NAME = "movie_db"

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}