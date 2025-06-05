package com.example.moviesapichallenge.domain.usecase

import androidx.paging.PagingData
import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularMoviesUseCas @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke(): Flow<PagingData<Movie>> {
        return repository.getPopularMovies()
    }
}