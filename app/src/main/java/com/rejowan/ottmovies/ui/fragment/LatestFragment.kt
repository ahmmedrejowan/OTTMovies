package com.rejowan.ottmovies.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.rejowan.ottmovies.adapter.MoviePaginationAdapter
import com.rejowan.ottmovies.adapter.MoviePaginationAdapter.OnMovieListener
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.databinding.FragmentLatestBinding
import com.rejowan.ottmovies.ui.activity.Details
import com.rejowan.ottmovies.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LatestFragment : Fragment() {


    private val binding by lazy { FragmentLatestBinding.inflate(layoutInflater) }
    private val movieViewModel: MovieViewModel by viewModel()

    private lateinit var moviePaginationAdapter: MoviePaginationAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        moviePaginationAdapter = MoviePaginationAdapter(mutableListOf(), onMovieListener = object : OnMovieListener {

            override fun onMovieClick(movieItem: MovieItem) {
                startActivity(Intent(requireContext(), Details::class.java).apply {
                    putExtra("movie", movieItem.imdbId)
                })
            }

            override fun onLastItemReach() {
                movieViewModel.getMovieList()
                binding.progressBar.visibility = View.VISIBLE
            }
        })

        binding.rvMovies.adapter = moviePaginationAdapter
        binding.rvMovies.layoutManager = GridLayoutManager(requireContext(), 3)

        movieViewModel.movieList.observe(viewLifecycleOwner) {
            it?.let { list ->
                moviePaginationAdapter.addMovies(list)
                binding.progressBar.visibility = View.GONE
            }
        }

    }

}