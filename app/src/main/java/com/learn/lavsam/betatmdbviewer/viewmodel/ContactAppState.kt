package com.learn.lavsam.betatmdbviewer.viewmodel

sealed class ContactAppState {
    class Success(val data: List<String>) : ContactAppState()
    object Loading : ContactAppState()
}
