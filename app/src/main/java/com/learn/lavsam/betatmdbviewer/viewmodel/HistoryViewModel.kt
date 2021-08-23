package com.learn.lavsam.betatmdbviewer.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.lavsam.betatmdbviewer.app.App
import com.learn.lavsam.betatmdbviewer.app.App.Companion.getHistoryDao
import com.learn.lavsam.betatmdbviewer.repository.LocalRepository
import com.learn.lavsam.betatmdbviewer.repository.LocalRepositoryImpl

class HistoryViewModel (
    val historyLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val historyRepository: LocalRepository = LocalRepositoryImpl(getHistoryDao())) : ViewModel() {

    fun getAllHistory() {
        historyLiveData.value = AppState.Loading
        historyLiveData.value = AppState.Success(historyRepository.getAllHistory())
    }
}