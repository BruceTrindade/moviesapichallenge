package com.example.moviesapichallenge.domain.repository

import androidx.paging.PagingData
import com.example.moviesapichallenge.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
    fun getPlayingNowMovies(): Flow<PagingData<Movie>>
    fun getTrendingMovies(): Flow<List<Movie>>

    fun getLocalMovies(): Flow<List<Movie>>
    suspend fun addToMyList(movie: Movie)
    suspend fun removeFromMyList(movieId: Int)
    suspend fun isLocal(movieId: Int): Boolean
    fun isLocalFlow(movieId: Int): Flow<Boolean>
    fun getMyListCount(): Flow<Int>

}