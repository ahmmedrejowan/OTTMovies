package com.rejowan.ottmovies.repositoryImpl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rejowan.ottmovies.constants.Config
import com.rejowan.ottmovies.data.remote.api.RetrofitClient
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

}