package com.example.moviesapichallenge.domain.usecase

import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    operator fun invoke() : Flow<List<Movie>> {
        return repository.getTrendingMovies()
    }
}