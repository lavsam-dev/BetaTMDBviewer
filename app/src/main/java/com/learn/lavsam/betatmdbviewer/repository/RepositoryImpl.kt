package com.learn.lavsam.betatmdbviewer.repository

import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.data.MovieList
import retrofit2.Callback

class RepositoryImpl(private val remoteDataSource: RemoteDataSource) : Repository {
    override fun getMovieFromServer() = MovieDetail()
    override fun getMoviesListFromServer(page: Int, callback: Callback<MovieList>) {
        remoteDataSource.getMovieList(page, callback)
    }
}