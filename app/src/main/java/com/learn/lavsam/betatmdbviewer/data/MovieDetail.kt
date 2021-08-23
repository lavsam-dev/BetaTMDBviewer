package com.learn.lavsam.betatmdbviewer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class MovieDetail(
    val id: Int? = 0,
    val original_title: String? = "",
    val title: String? = "",
    val release_date: String? = "",
    val overview: String? = "",
    val poster_path: String? = "",
    val vote_average: Double? = 0.0,
    val runtime: Int? = 0,
    val backdrop_path: String? = "",
    var note: String? = "",
    var look_time: String? = ""
) : Parcelable
