package com.learn.lavsam.betatmdbviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.lavsam.betatmdbviewer.BuildConfig
import com.learn.lavsam.betatmdbviewer.R
import com.learn.lavsam.betatmdbviewer.app.App
import com.learn.lavsam.betatmdbviewer.data.MovieList
import com.learn.lavsam.betatmdbviewer.repository.RemoteDataSource
import com.learn.lavsam.betatmdbviewer.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel() {
    val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData()
    private val repositoryImpl: RepositoryImpl =
        RepositoryImpl(RemoteDataSource())

    fun getMoviesListFromServer(page: Int, isAdult: Boolean) {
        liveDataToObserve.value = AppState.Loading
        repositoryImpl.getMoviesListFromServer(page, isAdult, callback)
    }

    private val callback = object : Callback<MovieList> {
        override fun onResponse(call: Call<MovieList>, response: Response<MovieList>) {
            val serverResponse: MovieList? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(App.context.getResources().getString(R.string.server_error)))
                }
            )
        }

        override fun onFailure(call: Call<MovieList>, t: Throwable) {
            liveDataToObserve.value = AppState.Error(Throwable(t.message ?:
            App.context.getResources().getString(R.string.request_error)))
        }
    }

    private fun checkResponse(serverResponse: MovieList): AppState {
        val movie = serverResponse.results
        return if (movie.isNullOrEmpty()) {
            AppState.Error(Throwable(App.context.getResources().getString(R.string.corrupted_data)))
        } else {
            AppState.Success(movie)
        }
    }
}