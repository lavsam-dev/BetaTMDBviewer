package com.learn.lavsam.betatmdbviewer.repository

import com.google.gson.GsonBuilder
import com.learn.lavsam.betatmdbviewer.BuildConfig
import com.learn.lavsam.betatmdbviewer.data.MovieList
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LANGUAGE = "en-US"
private const val MAIN_URL = "https://api.themoviedb.org/"
private const val TMDB_API_KEY = BuildConfig.TMDB_API_KEY

class RemoteDataSource {
    private val movieAPI = Retrofit.Builder()
        .baseUrl(MAIN_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build()
        .create(MovieAPI::class.java)

    fun getMovieList(page: Int, callback: Callback<MovieList>) {
        movieAPI.getMovieList(TMDB_API_KEY, LANGUAGE, page).enqueue(callback)
    }
}