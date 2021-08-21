package com.learn.lavsam.betatmdbviewer.repository

import com.learn.lavsam.betatmdbviewer.data.MovieDetail

interface LocalRepository {
    fun getAllHistory(): MutableList<MovieDetail>
    fun saveEntity(movie: MovieDetail)
}