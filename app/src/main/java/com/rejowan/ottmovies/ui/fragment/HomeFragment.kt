package com.rejowan.ottmovies.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.rejowan.ottmovies.adapter.MoviePortraitAdapter
import com.rejowan.ottmovies.adapter.MoviePosterAdapter
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.databinding.FragmentHomeBinding
import com.rejowan.ottmovies.ui.activity.Details
import com.rejowan.ottmovies.ui.activity.MovieList
import com.rejowan.ottmovies.utils.interfaces.OnMovieListener
import com.rejowan.ottmovies.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val movieViewModel: MovieViewModel by viewModel()

    private val sliderHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable = Runnable {
        binding.viewpager.currentItem += 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieViewModel.bannerMovies.observe(viewLifecycleOwner) {
            it?.let { response ->
                response.search?.let { list ->
                    setupSlider(list)
                }
            }
        }

        movieViewModel.batmanMovies.observe(viewLifecycleOwner) {
            it?.let { response ->
                setupBatmanMovies(response.toList())
            }
        }

        movieViewModel.latestMovies.observe(viewLifecycleOwner) {
            it?.let { response ->
                setupLatestMovies(response.toList())
            }
        }

        binding.seeMoreBatman.setOnClickListener {
            startActivity(Intent(requireContext(), MovieList::class.java).apply {
                putExtra("type", "batman")
            })
        }

        binding.seeMoreLatest.setOnClickListener {
            startActivity(Intent(requireContext(), MovieList::class.java).apply {
                putExtra("type", "latest")
            })
        }


    }

    private fun setupLatestMovies(list: List<MovieItem>) {
        val moviePortraitAdapter = MoviePortraitAdapter(list, onMovieListener = object : OnMovieListener {
            override fun onMovieClick(movieItem: MovieItem) {
                startActivity(Intent(requireContext(), Details::class.java).apply {
                    putExtra("movie", movieItem.imdbId)
                })
            }

            override fun onLastItemReach() {

            }

        })

        binding.rvLatest.adapter = moviePortraitAdapter
        binding.rvLatest.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


    }

    private fun setupBatmanMovies(list: List<MovieItem>) {
        val moviePortraitAdapter = MoviePortraitAdapter(list, onMovieListener = object : OnMovieListener {
            override fun onMovieClick(movieItem: MovieItem) {
                startActivity(Intent(requireContext(), Details::class.java).apply {
                    putExtra("movie", movieItem.imdbId)
                })
            }

            override fun onLastItemReach() {

            }

        })
        binding.rvBatman.adapter = moviePortraitAdapter
        binding.rvBatman.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


    }

    private fun setupSlider(list: List<MovieItem>) {
        val moviePosterAdapter = MoviePosterAdapter(ArrayList(list.take(5)), binding.viewpager, object : OnMovieListener {
            override fun onMovieClick(movieItem: MovieItem) {
                startActivity(Intent(requireContext(), Details::class.java).apply {
                    putExtra("movie", movieItem.imdbId)
                })
            }

            override fun onLastItemReach() {

            }

        })

        binding.viewpager.adapter = moviePosterAdapter
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 3000)
            }
        })


        binding.viewpager.setPageTransformer { page, position ->
            page.apply {
                val scaleFactor = 1 - Math.abs(position)
                scaleX = 0.85f + scaleFactor * 0.15f
                scaleY = 0.85f + scaleFactor * 0.15f
            }
        }


    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

}

