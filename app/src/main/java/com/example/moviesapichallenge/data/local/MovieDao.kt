package com.example.moviesapichallenge.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM local_movies ORDER BY addedAt DESC")
    fun getAllFavoriteMovies(): Flow<List<MovieLocal>>

    @Query("SELECT * FROM local_movies WHERE id = :movieId")
    suspend fun getFavoriteMovie(movieId: Int): MovieLocal?

    @Query("SELECT EXISTS(SELECT 1 FROM local_movies WHERE id = :movieId)")
    suspend fun isFavorite(movieId: Int): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM local_movies WHERE id = :movieId)")
    fun isFavoriteFlow(movieId: Int): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMovie(movie: MovieLocal)

    @Delete
    suspend fun deleteFavoriteMovie(movie: MovieLocal)

    @Query("DELETE FROM local_movies WHERE id = :movieId")
    suspend fun deleteFavoriteMovieById(movieId: Int)

    @Query("DELETE FROM local_movies")
    suspend fun deleteAllFavoriteMovies()

    @Query("SELECT COUNT(*) FROM local_movies")
    suspend fun getFavoriteMoviesCount(): Int

    @Query("SELECT COUNT(*) FROM local_movies")
    fun getFavoriteMoviesCountFlow(): Flow<Int>
}
