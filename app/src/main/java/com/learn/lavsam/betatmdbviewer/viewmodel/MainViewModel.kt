package com.learn.lavsam.betatmdbviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.lavsam.betatmdbviewer.BuildConfig
import com.learn.lavsam.betatmdbviewer.data.MovieList
import com.learn.lavsam.betatmdbviewer.repository.RemoteDataSource
import com.learn.lavsam.betatmdbviewer.repository.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = BuildConfig.SERVER_ERROR_MESSAGE
private const val REQUEST_ERROR = BuildConfig.REQUEST_ERROR_MESSAGE
private const val CORRUPTED_DATA = BuildConfig.CORRUPTED_DATA_MESSAGE

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
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieList>, t: Throwable) {
            liveDataToObserve.value = AppState.Error(Throwable(t.message ?: REQUEST_ERROR))
        }
    }

    private fun checkResponse(serverResponse: MovieList): AppState {
        val movie = serverResponse.results
        return if (movie.isNullOrEmpty()) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(movie)
        }
    }
}