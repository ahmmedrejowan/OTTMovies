package com.rejowan.ottmovies.utils.interfaces

import com.rejowan.ottmovies.data.remote.responses.MovieItem

interface OnMovieListener {
    fun onMovieClick(movieItem: MovieItem)
    fun onLastItemReach()

}