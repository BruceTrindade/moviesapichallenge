package com.example.moviesapichallenge.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapichallenge.databinding.FragmentHomeBinding
import com.example.moviesapichallenge.domain.model.Movie
import com.example.moviesapichallenge.utils.ImageSetting
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var imageSetting: ImageSetting

    private val sectionAdapter by lazy {
        SectionAdapter(
            onClick = { movie -> navigateToDetailsScreen(movie) },
            lifecycleOwner = this,
            imageSetting = imageSetting,
        )
    }

    private fun navigateToDetailsScreen(movie: Movie) {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewMovieSections.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sectionAdapter
        }

        lifecycleScope.launch {
            homeViewModel.movieSectionsState.collectLatest { movieSections ->
                sectionAdapter.submitList(movieSections)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}