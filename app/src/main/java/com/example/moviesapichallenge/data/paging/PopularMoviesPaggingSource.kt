package com.example.moviesapichallenge.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapichallenge.data.remote.api.TMDbApiService
import com.example.moviesapichallenge.data.remote.dto.toMovies
import com.example.moviesapichallenge.domain.model.Movie
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class PopularMoviesPagingSource @Inject constructor(
    private val apiService: TMDbApiService,
    private val apiKey: String,
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getPopularMovies(
                apiKey, page
            )

            val movies = response.results.toMovies()

            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (page >= response.totalPages) null else page + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

}