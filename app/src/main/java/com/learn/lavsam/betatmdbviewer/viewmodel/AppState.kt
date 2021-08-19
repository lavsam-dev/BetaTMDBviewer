package com.learn.lavsam.betatmdbviewer.viewmodel

import com.learn.lavsam.betatmdbviewer.data.MovieDetail

sealed class AppState {
    data class Success(val movieData: List<MovieDetail>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}

