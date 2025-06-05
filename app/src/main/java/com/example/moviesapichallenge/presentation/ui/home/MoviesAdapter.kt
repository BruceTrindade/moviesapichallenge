package com.example.moviesapichallenge.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviesapichallenge.R
import com.example.moviesapichallenge.databinding.ItemMovieSectionBinding
import com.example.moviesapichallenge.databinding.ItemMoviesBinding
import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.utils.ImageSetting
import com.example.moviesapichallenge.utils.getFullPosterUrl

class MoviesAdapter(
    private val onClick: (Movie) -> Unit = {},
    private val imageSettings: ImageSetting
) : PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(MovieDiff) {

    inner class MovieViewHolder(
        private val binding: ItemMoviesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                textMovieTitle.text = movie.title
                Glide.with(imageMoviePoster)
                    .load(movie.getFullPosterUrl(imageSettings))
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .error(R.drawable.outline_animated_images_24)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(imageMoviePoster)
            }
        }
    }

    override fun onBindViewHolder(holder: MoviesAdapter.MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) holder.bind(movie)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MovieViewHolder {
        val binding = ItemMoviesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false,
        )
        return MovieViewHolder(binding)
    }

}

object MovieDiff : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}