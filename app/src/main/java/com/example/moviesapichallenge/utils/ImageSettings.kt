package com.example.moviesapichallenge.utils

import com.example.moviesapichallenge.domain.model.Movie

data class ImageSetting(
    val posterBaseUrl: String,
    val backdropBaseUrl: String
)

fun Movie.getFullPosterUrl(imageConfig: ImageSetting): String? {
    return posterPath?.let { "${imageConfig.posterBaseUrl}$it" }
}

fun Movie.getFullBackdropUrl(imageConfig: ImageSetting): String? {
    return backdropPath?.let { "${imageConfig.backdropBaseUrl}$it" }
}