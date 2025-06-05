package com.example.moviesapichallenge.presentation.ui.home

import com.example.moviesapichallenge.domain.model.Movie

data class UiState(
    val trending: List<Movie> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
)