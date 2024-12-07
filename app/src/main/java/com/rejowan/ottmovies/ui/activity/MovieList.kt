package com.rejowan.ottmovies.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.GridLayoutManager
import com.rejowan.ottmovies.adapter.MoviePaginationAdapter
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.databinding.ActivityMovieListBinding
import com.rejowan.ottmovies.utils.interfaces.OnMovieListener
import com.rejowan.ottmovies.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieList : AppCompatActivity() {

    private val binding by lazy { ActivityMovieListBinding.inflate(layoutInflater) }

    private lateinit var movieType: String
    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var moviePaginationAdapter: MoviePaginationAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        movieType = intent.getStringExtra("type").toString()

        moviePaginationAdapter = MoviePaginationAdapter(mutableListOf(), onMovieListener = object : OnMovieListener {

            override fun onMovieClick(movieItem: MovieItem) {
                startActivity(Intent(this@MovieList, Details::class.java).apply {
                    putExtra("movie", movieItem.imdbId)
                })
            }

            override fun onLastItemReach() {
                if (movieType == "latest") {
                    movieViewModel.getLatestMovies()
                } else if (movieType == "batman") {
                    movieViewModel.getBatmanMovies()
                } else {
                    movieViewModel.getMissionImpossibleMovies()
                }

                binding.progressBar.visibility = View.VISIBLE
            }
        })

        binding.rvMovies.adapter = moviePaginationAdapter
        binding.rvMovies.layoutManager = GridLayoutManager(this, 2)


        if (movieType == "latest") {
            binding.title.text = "Latest Movies (2022) (War)"
            movieViewModel.latestMovies.observe(this) {
                it?.let { response ->
                    moviePaginationAdapter.addMovies(response.toList())
                    binding.progressBar.visibility = View.GONE
                }
            }

        } else if (movieType == "batman") {
            binding.title.text = "Batman Movies"

            movieViewModel.batmanMovies.observe(this) {
                it?.let { response ->
                    moviePaginationAdapter.addMovies(response.toList())
                    binding.progressBar.visibility = View.GONE
                }
            }
        } else {
            binding.title.text = "Mission Impossible Movies"

            movieViewModel.missionImpossibleMovies.observe(this) {
                it?.let { response ->
                    moviePaginationAdapter.addMovies(response.toList())
                    binding.progressBar.visibility = View.GONE
                }
            }
        }


        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }
}