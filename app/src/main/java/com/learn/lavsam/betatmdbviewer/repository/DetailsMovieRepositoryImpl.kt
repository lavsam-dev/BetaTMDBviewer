package com.learn.lavsam.betatmdbviewer.repository

import com.learn.lavsam.betatmdbviewer.dto.MovieDetailDTO
import retrofit2.Callback

class DetailsMovieRepositoryImpl (private val remoteDataSource: RemoteDataSource) : DetailsMovieRepository {
    override fun getMovieDetailsFromServer(id: Int?, callback: Callback<MovieDetailDTO>) {
        remoteDataSource.getMovieDetails(id, callback)
    }
}