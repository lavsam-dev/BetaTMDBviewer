package com.learn.lavsam.betatmdbviewer.repository

import com.learn.lavsam.betatmdbviewer.dto.MovieDetailDTO
import retrofit2.Callback

interface DetailsMovieRepository {
    fun getMovieDetailsFromServer (
        id: Int?,
        callback: Callback<MovieDetailDTO>
    )
}