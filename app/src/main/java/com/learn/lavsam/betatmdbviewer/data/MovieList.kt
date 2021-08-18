package com.learn.lavsam.betatmdbviewer.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MovieList(
    val results: ArrayList<MovieDetail>
) : Parcelable
