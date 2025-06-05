package com.example.moviesapichallenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesapichallenge.data.paging.PlayingNowMoviesPagingSource
import com.example.moviesapichallenge.data.paging.PopularMoviesPagingSource
import com.example.moviesapichallenge.data.remote.api.TMDbApiService
import com.example.moviesapichallenge.data.remote.dto.toMovies
import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: TMDbApiService,
    private val apiKey: String,
): MovieRepository {

    companion object {
        const val PAGE_SIZE = 20
        const val PREFETCH_DISTANCE = 5
        const val TRENDING_LIMIT = 5
        const val SIMILAR_LIMIT = 10
    }

    override fun getPopularMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                PopularMoviesPagingSource(apiService = apiService, apiKey = apiKey)
            }
        ).flow
    }

    override fun getPlayingNowMovies(): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = PREFETCH_DISTANCE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = {
                PlayingNowMoviesPagingSource(apiService = apiService, apiKey = apiKey)
            }
        ).flow
    }

    override fun getTrendingMovies(): Flow<List<Movie>> = flow {
        try {
            val response = apiService.getTrendingMovies(
                apiKey = apiKey,
                page = 1
            )

            val movies = response.results.toMovies()
            emit(movies)
        } catch (e: Exception) {
            emit(emptyList())
        }
    }


}