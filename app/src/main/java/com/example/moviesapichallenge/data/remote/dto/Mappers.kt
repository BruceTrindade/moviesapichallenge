package com.example.moviesapichallenge.data.remote.dto

import com.example.moviesapichallenge.domain.model.Movie

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount,
        popularity = popularity,
        adult = adult,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        genreIds = genreIds
    )
}

fun List<MovieDto>.toMovies(): List<Movie> {
    return map { it.toMovie() }
}