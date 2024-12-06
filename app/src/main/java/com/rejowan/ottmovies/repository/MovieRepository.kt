package com.rejowan.ottmovies.repository

import androidx.lifecycle.LiveData
import com.rejowan.ottmovies.data.remote.responses.MovieSearchResponse

interface MovieRepository {

    val bannerMovies : LiveData<MovieSearchResponse?>

    val batmanMovies : LiveData<MovieSearchResponse?>

    suspend fun getBannerMovies()

    suspend fun getBatmanMovies()


}