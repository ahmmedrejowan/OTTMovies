package com.rejowan.ottmovies.repository

import androidx.lifecycle.LiveData
import com.rejowan.ottmovies.data.remote.responses.MovieDetailsResponse
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.data.remote.responses.MovieSearchResponse

interface MovieRepository {

    val bannerMovies: LiveData<MovieSearchResponse?>

    val batmanMovies: LiveData<MovieSearchResponse?>

    val latestMovies: LiveData<MovieSearchResponse?>

    val movieList: LiveData<MutableList<MovieItem>>

    suspend fun getBannerMovies()

    suspend fun getBatmanMovies()

    suspend fun getLatestMovies()

    suspend fun getMovieList()

    suspend fun getMovieDetails(imdbID: String, callback: (MovieDetailsResponse?) -> Unit)

}