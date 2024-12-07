package com.rejowan.ottmovies.model

data class PlayHistory(
    var id: Int = -1,
    var movieId: String,
    var movieUrl: String,
    var timestamp: String
)
