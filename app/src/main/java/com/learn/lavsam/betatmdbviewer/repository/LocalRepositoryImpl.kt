package com.learn.lavsam.betatmdbviewer.repository

import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.data.convertHistoryEntityToMovie
import com.learn.lavsam.betatmdbviewer.data.convertMovieToHistoryEntity
import com.learn.lavsam.betatmdbviewer.room.HistoryDao
import com.learn.lavsam.betatmdbviewer.room.HistoryEntity

class LocalRepositoryImpl (private val localDataSourceHistory: HistoryDao) : LocalRepository {
    override fun getAllHistory(): MutableList<MovieDetail> {
        return convertHistoryEntityToMovie(localDataSourceHistory.all())
    }

    override fun saveHistoryEntity(movieDetail: MovieDetail) {
        return localDataSourceHistory.insert(convertMovieToHistoryEntity(movieDetail))
    }


}