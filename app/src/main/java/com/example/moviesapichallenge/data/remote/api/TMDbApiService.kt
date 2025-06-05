package com.example.moviesapichallenge.data.remote.api

import com.example.moviesapichallenge.data.remote.dto.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDbApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse
}