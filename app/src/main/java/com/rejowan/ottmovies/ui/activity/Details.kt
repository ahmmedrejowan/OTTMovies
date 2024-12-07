package com.rejowan.ottmovies.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rejowan.ottmovies.R
import com.rejowan.ottmovies.data.remote.responses.MovieDetailsResponse
import com.rejowan.ottmovies.databinding.ActivityDetailsBinding
import com.rejowan.ottmovies.viewmodel.MovieViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class Details : AppCompatActivity() {

    private val binding by lazy { ActivityDetailsBinding.inflate(layoutInflater) }
    private val movieViewModel: MovieViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val movieId = intent.getStringExtra("movie")
        movieId?.let {
            movieViewModel.getMovieDetails(it) { movie ->
                movie?.let {
                    updateView(movie)
                }
            }
        }

        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }


    }

    private fun updateView(movie: MovieDetailsResponse) {
        Glide.with(this@Details).load(movie.poster).placeholder(R.drawable.img_placeholder_landscape)
            .error(R.drawable.img_placeholder_landscape).centerCrop().into(binding.ivPoster)

        binding.title.text = movie.title
        binding.tvTitle.text = movie.title
        binding.tvYear.text = "(${movie.year})"
        binding.tvGenre.text = movie.genre
        binding.tvLanguage.text = movie.language
        binding.tvCountry.text = movie.country
        binding.tvReleased.text = movie.released

        binding.tvRuntime.text = movie.runtime
        binding.tvRating.text = movie.imdbRating
        binding.tvBoxOffice.text = movie.boxOffice

        binding.tvActor.text = movie.actors
        binding.tvWriter.text = movie.writer
        binding.tvDirectory.text = movie.director

        binding.tvPlot.text = movie.plot

        binding.tvAwards.text = movie.awards


    }


}