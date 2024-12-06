package com.rejowan.ottmovies.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.rejowan.ottmovies.adapter.MoviePosterAdapter
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.databinding.FragmentHomeBinding
import com.rejowan.ottmovies.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs


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
                response.search?.let { list ->
                    setupBatmanMovies(list)
                }
            }
        }


    }

    private fun setupBatmanMovies(list: List<MovieItem>) {


    }

    private fun setupSlider(list: List<MovieItem>) {
        val moviePosterAdapter = MoviePosterAdapter(ArrayList(list.take(5)), binding.viewpager)
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

