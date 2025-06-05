package com.example.moviesapichallenge.domain.repository

import androidx.paging.PagingData
import com.example.moviesapichallenge.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<PagingData<Movie>>
}