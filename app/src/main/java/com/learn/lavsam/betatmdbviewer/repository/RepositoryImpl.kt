package com.learn.lavsam.betatmdbviewer.repository

import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.data.MovieList
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getMovieFromServer() = MovieDetail()
    override fun getMoviesListFromServer(page: Int, isAdult: Boolean, callback: Callback<MovieList>) {
        remoteDataSource.getMovieList(page, isAdult, callback)
    }
}