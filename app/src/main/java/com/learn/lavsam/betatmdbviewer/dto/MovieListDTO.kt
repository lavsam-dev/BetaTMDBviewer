package com.learn.lavsam.betatmdbviewer.dto

import com.learn.lavsam.betatmdbviewer.data.MovieDetail

data class MovieListDTO(
    val page: Int,
    val list: ArrayList<MovieDetail>
)
