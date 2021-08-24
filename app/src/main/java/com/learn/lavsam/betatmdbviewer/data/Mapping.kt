package com.learn.lavsam.betatmdbviewer.data

import com.learn.lavsam.betatmdbviewer.dto.MovieDetailDTO
import com.learn.lavsam.betatmdbviewer.room.HistoryEntity

fun convertDtoToModel(movieDTO: MovieDetailDTO): List<MovieDetail> {
    return listOf(
        MovieDetail(
            movieDTO?.id,
            movieDTO.original_title,
            movieDTO.title,
            movieDTO.release_date,
            movieDTO.overview,
            movieDTO.poster_path.toString(),
            movieDTO.vote_average,
            movieDTO.runtime,
            movieDTO.backdrop_path.toString()
        )
    )
}

fun convertHistoryEntityToMovie(entityList: List<HistoryEntity>): MutableList<MovieDetail> {
    return entityList.map {
        MovieDetail(
            id = it.film_Id?.toInt(),
            title = it.title,
            poster_path = it.poster_path,
            runtime = it.runtime,
            vote_average = it.vote_average,
            release_date = it.release_date,
            backdrop_path = it.backdrop_path,
            overview = it.overview,
            note = it.note,
            look_time = it.look_time
        )
    }.toMutableList()
}

fun convertMovieToHistoryEntity(movieDetail: MovieDetail): HistoryEntity {
    return HistoryEntity(
        0,
        movieDetail.id,
        movieDetail.title,
        movieDetail.poster_path,
        movieDetail.runtime,
        movieDetail.vote_average,
        movieDetail.release_date,
        movieDetail.backdrop_path,
        movieDetail.overview,
        movieDetail.note,
        movieDetail.look_time
    )
}

