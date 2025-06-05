package com.example.moviesapichallenge.domain.usecase.mylist

import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.domain.repository.MovieRepository
import javax.inject.Inject

class AddToMyListUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(movie: Movie) {
        repository.addToMyList(movie)
    }
}