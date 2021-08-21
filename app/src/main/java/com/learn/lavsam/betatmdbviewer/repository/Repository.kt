package com.learn.lavsam.betatmdbviewer.repository

import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.data.MovieList
import retrofit2.Callback

interface Repository {
    fun getMovieFromServer() : MovieDetail
    fun getMoviesListFromServer(page: Int, isAdult: Boolean, callback: Callback<MovieList>)
}