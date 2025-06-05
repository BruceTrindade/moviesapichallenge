package com.example.moviesapichallenge.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.domain.usecase.GetPlayingNowMovieUseCase
import com.example.moviesapichallenge.domain.usecase.GetPopularMoviesUseCas
import com.example.moviesapichallenge.domain.usecase.GetTrendingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCas,
    private val getPlayingNowMoviesUseCase: GetPlayingNowMovieUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase
) : ViewModel() {

    private val getPopularMoviesPagingData = getPopularMoviesUseCase().cachedIn(viewModelScope)
    private val getPlayingNowMoviesPagingData = getPlayingNowMoviesUseCase().cachedIn(viewModelScope)
    private val getTrendingMoviesList = getTrendingMoviesUseCase()

    private val _movieSection = MutableStateFlow<List<MovieSection>>(emptyList())
    val movieSectionsState: StateFlow<List<MovieSection>> = _movieSection.asStateFlow()

    private val _popularMoviesPagingData = MutableStateFlow<PagingData<Movie>?>(null)
    private val _playingNowMoviesPagingData = MutableStateFlow<PagingData<Movie>?>(null)

    init {
        setMovieSections()
        fetchPagingData()
    }

    private fun fetchPagingData() {
        viewModelScope.launch {
            getPopularMoviesPagingData.collectLatest {
                _popularMoviesPagingData.value = it
            }
        }

        viewModelScope.launch {
            getPlayingNowMoviesPagingData.collectLatest {
                _playingNowMoviesPagingData.value = it
            }
        }
    }

    private fun setMovieSections() {
        viewModelScope.launch {
            combine(
                getTrendingMoviesList,
                _popularMoviesPagingData,
                _playingNowMoviesPagingData
            ) { trendingMovies, popularMovies, playingNowMovies ->
                listOf(
                    MovieSection(
                        title = "Em alta",
                        movies = trendingMovies,
                        sectionType = SectionType.TRENDING
                    ),
                    MovieSection(
                        title = "Popular",
                        pagingData = popularMovies,
                        sectionType = SectionType.POPULAR
                    ),
                    MovieSection(
                        title = "Em cartaz",
                        pagingData = playingNowMovies,
                        sectionType = SectionType.NOW_PLAYING
                    ),
                )

            }.collect { sections ->
                _movieSection.update { sections }
            }
        }

    }


}