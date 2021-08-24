package com.learn.lavsam.betatmdbviewer.repository

import com.google.gson.GsonBuilder
import com.learn.lavsam.betatmdbviewer.BuildConfig
import com.learn.lavsam.betatmdbviewer.data.MovieList
import com.learn.lavsam.betatmdbviewer.dto.MovieDetailDTO
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LANGUAGE = BuildConfig.LANGUAGE_CONST
private const val MAIN_URL = BuildConfig.MAIN_URL_CONST
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

    fun getMovieDetails(id: Int?, callback: Callback<MovieDetailDTO>) {
        movieAPI.getMovie(id, TMDB_API_KEY, LANGUAGE).enqueue(callback)
    }

    fun getMovieList(page: Int, isAdult: Boolean, callback: Callback<MovieList>) {
        movieAPI.getMovieList(TMDB_API_KEY, LANGUAGE, page, isAdult).enqueue(callback)
    }
}