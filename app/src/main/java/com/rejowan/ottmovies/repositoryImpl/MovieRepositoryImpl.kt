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

    private val _batmanMovies = MutableLiveData<MutableList<MovieItem>>()
    override val batmanMovies: LiveData<MutableList<MovieItem>>
        get() = _batmanMovies

    private val _latestMovies = MutableLiveData<MutableList<MovieItem>>()
    override val latestMovies: LiveData<MutableList<MovieItem>>
        get() = _latestMovies

    private val _missionImpossibleMovies = MutableLiveData<MutableList<MovieItem>>()
    override val missionImpossibleMovies: LiveData<MutableList<MovieItem>>
        get() = _missionImpossibleMovies


    private val _movieList = MutableLiveData<MutableList<MovieItem>>()
    override val movieList: LiveData<MutableList<MovieItem>>
        get() = _movieList

    private var listCurrentPage = 1
    private var batmanCurrentPage = 1
    private var latestCurrentPage = 1
    private var missionImpossibleCurrentPage = 1


    // pirates movies for banner
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

    // batman movies for batman list
    override suspend fun getBatmanMovies() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie("Batman", page = batmanCurrentPage)?.await()
            response?.search?.let {
                val updatedList = _batmanMovies.value?.toMutableList() ?: mutableListOf()
                updatedList.addAll(it)
                _batmanMovies.postValue(updatedList)
                batmanCurrentPage++
            }
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getBatmanMovies: ${e.message}", e)

        }
    }

    // 2022 movies for latest movies
    override suspend fun getLatestMovies() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie(search = "War", year = "2022", page = latestCurrentPage)?.await()
            response?.search?.let {
                val updatedList = _latestMovies.value?.toMutableList() ?: mutableListOf()
                updatedList.addAll(it)
                _latestMovies.postValue(updatedList)
                latestCurrentPage++
            }

        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getLatestMovies: ${e.message}", e)

        }
    }

    // mission impossible movies for mission impossible list
    override suspend fun getMissionImpossibleMovies() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie("Mission Impossible", page = missionImpossibleCurrentPage)?.await()
            response?.search?.let {
                val updatedList = _missionImpossibleMovies.value?.toMutableList() ?: mutableListOf()
                updatedList.addAll(it)
                _missionImpossibleMovies.postValue(updatedList)
                missionImpossibleCurrentPage++
            }
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getMissionImpossibleMovies: ${e.message}", e)
        }
    }

    // list movies for list fragment page
    override suspend fun getMovieList() {
        try {
            val response = RetrofitClient.getInstance(
                Config.BASE_URL
            )?.searchMovie("War", page = listCurrentPage)?.await()
            response?.search?.let {
                val updatedList = _movieList.value?.toMutableList() ?: mutableListOf()
                updatedList.addAll(it)
                _movieList.postValue(updatedList)
                listCurrentPage++
            }
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "getMovieList: ${e.message}", e)
        }

    }

    // individual movie details
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

}