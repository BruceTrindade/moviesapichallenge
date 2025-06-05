package com.example.moviesapichallenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.moviesapichallenge.data.local.MovieDao
import com.example.moviesapichallenge.data.local.toMovie
import com.example.moviesapichallenge.data.local.toMovieLocal
import com.example.moviesapichallenge.data.paging.PlayingNowMoviesPagingSource
import com.example.moviesapichallenge.data.paging.PopularMoviesPagingSource
import com.example.moviesapichallenge.data.remote.api.TMDbApiService
import com.example.moviesapichallenge.data.remote.dto.toMovies
import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val apiService: TMDbApiService,
    private val localMovieDao: MovieDao,
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

    override fun getLocalMovies(): Flow<List<Movie>> {
        return localMovieDao.getAllFavoriteMovies().map { myListMovies ->
            myListMovies.map { it.toMovie() }
        }
    }

    override suspend fun addToMyList(movie: Movie) {
        localMovieDao.insertFavoriteMovie((movie.toMovieLocal()))
    }

    override suspend fun removeFromMyList(movieId: Int) {
        localMovieDao.deleteFavoriteMovieById(movieId)
    }

    override suspend fun isLocal(movieId: Int): Boolean {
        return localMovieDao.isFavorite(movieId)
    }

    override fun isLocalFlow(movieId: Int): Flow<Boolean> {
        return localMovieDao.isFavoriteFlow(movieId)
    }

    override fun getMyListCount(): Flow<Int> {
        return localMovieDao.getFavoriteMoviesCountFlow()
    }


}