package com.example.moviesapichallenge.presentation.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapichallenge.R
import com.example.moviesapichallenge.databinding.ItemMovieSectionBinding
import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.utils.ImageSetting

data class MovieSection(
    val title: String,
    val movies: List<Movie> = emptyList(),
    val pagingData: PagingData<Movie>? = null,
    val sectionType: SectionType
)

enum class SectionType {
    TRENDING,
    NOW_PLAYING,
    POPULAR
}

class SectionAdapter(
    private val onClick: (Movie) -> Unit,
    private val lifecycleOwner: LifecycleOwner,
    private val imageSetting: ImageSetting,
) : ListAdapter<MovieSection, SectionAdapter.SectionViewHolder>(MovieSectionDiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionAdapter.SectionViewHolder {
        val binding = ItemMovieSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionAdapter.SectionViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SectionViewHolder(private val binding: ItemMovieSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(section: MovieSection) {
            binding.apply {
                sectionTitle.text = section.title

                val movieAdapter = MoviesAdapter(onClick, imageSetting)
                section.pagingData?.let {
                    movieAdapter.submitData(lifecycleOwner.lifecycle, it)
                }

                recyclerViewMovies.apply {
                    adapter = movieAdapter
                    layoutManager = LinearLayoutManager(
                        context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                }
            }
        }

    }
}

object MovieSectionDiffCallback : DiffUtil.ItemCallback<MovieSection>() {
    override fun areItemsTheSame(oldItem: MovieSection, newItem: MovieSection): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: MovieSection, newItem: MovieSection): Boolean {
        return oldItem == newItem
    }
}