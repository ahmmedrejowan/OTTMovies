package com.rejowan.ottmovies.repositoryImpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rejowan.ottmovies.constants.Config
import com.rejowan.ottmovies.data.remote.api.RetrofitClient
import com.rejowan.ottmovies.data.remote.responses.MovieDetailsResponse
import com.rejowan.ottmovies.data.remote.responses.MovieItem
import com.rejowan.ottmovies.data.remote.responses.MovieSearchResponse
import com.rejowan.ottmovies.repository.MovieRepository
import retrofit2.await

class MovieRepositoryImpl : MovieRepository {


    private val _bannerMovies = MutableLiveData<MovieSearchResponse?>()
    override val bannerMovies: LiveData<MovieSearchResponse?>
        get() = _bannerMovies

    private val _batmanMovies = MutableLiveData<MovieSearchResponse?>()
    override val batmanMovies: LiveData<MovieSearchResponse?>
        get() = _batmanMovies

    private val _latestMovies = MutableLiveData<MovieSearchResponse?>()
    override val latestMovies: LiveData<MovieSearchResponse?>
        get() = _latestMovies

    private val _movieList = MutableLiveData<MutableList<MovieItem>>()
    override val movieList: LiveData<MutableList<MovieItem>>
        get() = _movieList

    private var currentPage = 1

    override suspend fun getMovieList() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie("War", page = currentPage)?.await()
            response?.search?.let {
                val updatedList = _movieList.value?.toMutableList() ?: mutableListOf()
                updatedList.addAll(it)
                _movieList.postValue(updatedList)
                currentPage++
            }
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getMovieList: ${e.message}", e)
        }

    }

    override suspend fun getMovieDetails(imdbID: String, callback: (MovieDetailsResponse?) -> Unit) {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.getMovieDetails(imdbId = imdbID)?.await()
            response?.let {
                callback(it)
            }
        } catch (e: Exception) {
            callback(null)
            Log.e("MovieRepositoryImpl", "getMovieDetails: ${e.message}", e)
        }
    }


    override suspend fun getBannerMovies() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie("Pirates")?.await()
            _bannerMovies.postValue(response)
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getBannerMovies: ${e.message}", e)
            _bannerMovies.postValue(null)

        }

    }

    override suspend fun getBatmanMovies() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie("Batman")?.await()
            _batmanMovies.postValue(response)
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getBatmanMovies: ${e.message}", e)
            _batmanMovies.postValue(null)

        }
    }


    override suspend fun getLatestMovies() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie(search = "War", year = "2022")?.await()
            _latestMovies.postValue(response)
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getLatestMovies: ${e.message}", e)
            _latestMovies.postValue(null)

        }
    }


}