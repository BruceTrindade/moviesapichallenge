package com.example.moviesapichallenge.di

import android.content.Context
import androidx.room.Room
import com.example.moviesapichallenge.data.local.MovieDao
import com.example.moviesapichallenge.data.local.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            MovieDatabase.DATABASE_NAME,
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideFavoriteMovieDao(database: MovieDatabase): MovieDao {
        return database.favoriteMovieDao()
    }
}