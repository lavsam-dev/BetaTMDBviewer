package com.learn.lavsam.betatmdbviewer.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val film_Id: Int?,
    val title: String?,
    val poster_path: String?,
    val runtime: Int?,
    val vote_average: Double?,
    val release_date: String?,
    val backdrop_path: String?,
    val overview: String?,
    val note: String?,
    val look_time: String?
)
