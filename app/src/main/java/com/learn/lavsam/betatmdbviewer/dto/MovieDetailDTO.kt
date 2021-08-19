package com.learn.lavsam.betatmdbviewer.dto

data class MovieDetailDTO(
    val id: Int?,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,
    val runtime: Int?
)
