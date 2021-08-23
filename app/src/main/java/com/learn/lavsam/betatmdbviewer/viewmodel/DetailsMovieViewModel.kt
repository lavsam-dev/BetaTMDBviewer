package com.learn.lavsam.betatmdbviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.lavsam.betatmdbviewer.BuildConfig
import com.learn.lavsam.betatmdbviewer.app.App.Companion.getHistoryDao
import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.data.convertDtoToModel
import com.learn.lavsam.betatmdbviewer.dto.MovieDetailDTO
import com.learn.lavsam.betatmdbviewer.repository.*
import com.learn.lavsam.betatmdbviewer.view.getCurrentDateTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

private const val SERVER_ERROR = BuildConfig.SERVER_ERROR_MESSAGE
private const val REQUEST_ERROR = BuildConfig.REQUEST_ERROR_MESSAGE
private const val CORRUPTED_DATA = BuildConfig.CORRUPTED_DATA_MESSAGE

class DetailsMovieViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsMovieRepositoryImpl: DetailsMovieRepository =
        DetailsMovieRepositoryImpl(RemoteDataSource()),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())
) : ViewModel() {

    fun getMovieFromRemoteSource(id: Int?) {
        detailsLiveData.value = AppState.Loading
        detailsMovieRepositoryImpl.getMovieDetailsFromServer(id, callBack)
    }

    fun saveMovieToDB(movie: MovieDetail) {
        movie.look_time = getCurrentDateTime()
        Thread {
            historyRepository.saveHistoryEntity(movie)
        }.start()
    }

    private val callBack = object :
        Callback<MovieDetailDTO> {

        @Throws(IOException::class)
        override fun onResponse(call: Call<MovieDetailDTO>, response: Response<MovieDetailDTO>) {
            val serverResponse: MovieDetailDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<MovieDetailDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }
    }

    private fun checkResponse(serverResponse: MovieDetailDTO): AppState {
        return if (serverResponse.id == null || serverResponse.original_title == null ||
            serverResponse.overview == null || serverResponse.poster_path == null || serverResponse.backdrop_path == null ||
            serverResponse.release_date == null || serverResponse.title == null || serverResponse.vote_average == null ||
            serverResponse.runtime == null
        ) {
            AppState.Error(Throwable(CORRUPTED_DATA))
        } else {
            AppState.Success(convertDtoToModel(serverResponse))
        }
    }

}