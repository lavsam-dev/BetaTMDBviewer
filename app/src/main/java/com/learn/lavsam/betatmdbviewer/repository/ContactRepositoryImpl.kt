package com.learn.lavsam.betatmdbviewer.repository

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import com.learn.lavsam.betatmdbviewer.app.ContextProvider
import com.learn.lavsam.betatmdbviewer.app.IContextProvider

class ContactRepositoryImpl(contextProvider: IContextProvider = ContextProvider) :
    ContactRepository {

    private val contentResolver: ContentResolver = contextProvider.context.contentResolver

    override fun getListofContact(): List<String> {
        val cursorWithContacts: Cursor? = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            null,
            null,
            null,
            ContactsContract.Contacts.DISPLAY_NAME + " ASC"
        )

        val answer = mutableListOf<String>()

        cursorWithContacts?.let { cursor ->
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                val contactDetail =
                    cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                answer.add(contactDetail)
                cursor.moveToNext()
            }
            cursorWithContacts?.close()
        }
        return answer
    }
}