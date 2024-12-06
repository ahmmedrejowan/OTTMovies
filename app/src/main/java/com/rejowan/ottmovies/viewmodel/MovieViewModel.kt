package com.rejowan.ottmovies.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rejowan.ottmovies.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val bannerMovies = repository.bannerMovies

    val batmanMovies = repository.batmanMovies

    val latestMovies = repository.latestMovies

    val movieList = repository.movieList

    fun getBannerMovies() {
        viewModelScope.launch {
            repository.getBannerMovies()
        }
    }

    fun getBatmanMovies() {
        viewModelScope.launch {
            repository.getBatmanMovies()
        }
    }


    fun getLatestMovies() {
        viewModelScope.launch {
            repository.getLatestMovies()
        }
    }

    fun getMovieList() {
        viewModelScope.launch {
            repository.getMovieList()
        }
    }


}