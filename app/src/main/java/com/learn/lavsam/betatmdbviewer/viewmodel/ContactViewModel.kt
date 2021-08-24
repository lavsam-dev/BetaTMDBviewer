package com.learn.lavsam.betatmdbviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.learn.lavsam.betatmdbviewer.repository.ContactRepository
import com.learn.lavsam.betatmdbviewer.repository.ContactRepositoryImpl

class ContactViewModel(private val repository: ContactRepository = ContactRepositoryImpl()) : ViewModel() {
    val contacts: MutableLiveData<ContactAppState> = MutableLiveData()

    fun getContacts() {
        contacts.value = ContactAppState.Loading
        val answer = repository.getListofContact()
        contacts.value = ContactAppState.Success(answer)
    }
}