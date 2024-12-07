package com.rejowan.ottmovies.data.remote.api

import com.rejowan.ottmovies.constants.Config
import com.rejowan.ottmovies.data.remote.responses.MovieDetailsResponse
import com.rejowan.ottmovies.data.remote.responses.MovieSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {


    @GET("/")
    fun searchMovie(
        @Query("s") search: String,
        @Query("y") year: String = "",
        @Query("type") type: String = "movie",
        @Query("page") page: Int = 1,
        @Query("apikey") apiKey: String = Config.API_KEY
    ): Call<MovieSearchResponse>


    @GET("/")
    fun getMovieDetails(
        @Query("i") imdbId: String = "",
        @Query("t") title: String = "",
        @Query("plot") plot: String = "short",
        @Query("apikey") apiKey: String = Config.API_KEY
    ): Call<MovieDetailsResponse>


}