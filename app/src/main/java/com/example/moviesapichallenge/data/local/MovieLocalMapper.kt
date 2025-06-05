package com.example.moviesapichallenge.data.local

import com.example.moviesapichallenge.domain.model.Movie

fun Movie.toMovieLocal(): MovieLocal {
    return MovieLocal(
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
        genreIds = genreIds.joinToString(",")
    )
}

fun MovieLocal.toMovie(): Movie {
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
        genreIds = if (genreIds.isBlank()) emptyList() else genreIds.split(",").mapNotNull { it.toIntOrNull() }
    )
}
