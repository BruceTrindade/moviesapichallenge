package com.example.moviesapichallenge.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local_movies")
data class MovieLocal(
    @PrimaryKey
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String?,
    val backdropPath: String?,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val popularity: Double,
    val adult: Boolean,
    val originalLanguage: String,
    val originalTitle: String,
    val genreIds: String,
    val addedAt: Long = System.currentTimeMillis()
)