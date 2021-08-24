package com.learn.lavsam.betatmdbviewer.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.learn.lavsam.betatmdbviewer.R
import java.text.SimpleDateFormat
import java.util.*

const val DIALOG_NOTE_MAXLINE = 3
const val DIALOG_NOTE_PADDINGLEFT = 45
const val DIALOG_NOTE_PADDINGTOP = 15
const val DIALOG_NOTE_PADDINGRIGHT = 45
const val DIALOG_NOTE_PADDINGBOTTOM = 0
const val DATE_FORMAT = "dd-MM-yyyy"
const val TIME_FORMAT = "HH:mm"

fun View.showSnackBar(
    text: String,
    actionText: String,
    action: (View) -> Unit,
    length: Int = Snackbar.LENGTH_INDEFINITE
) {
    Snackbar.make(this, text, length)
        .setAction(actionText, action)
        .show()
}

fun getCurrentDateTime(): String {
    val formattedDate = SimpleDateFormat(DATE_FORMAT).format(Date())
    val formattedTime = SimpleDateFormat(TIME_FORMAT).format(Date())
    return "$formattedDate  $formattedTime"
}

fun View.showPostDialog(title: String): String {

    var note: String = ""
    val alert = AlertDialog.Builder(this.context)

    val edittext = EditText(this.context)
    edittext.hint = title
    edittext.maxLines = DIALOG_NOTE_MAXLINE

    var layout = this.context?.let { FrameLayout(it) }

    layout?.setPadding(
        DIALOG_NOTE_PADDINGLEFT, DIALOG_NOTE_PADDINGTOP, DIALOG_NOTE_PADDINGRIGHT,
        DIALOG_NOTE_PADDINGBOTTOM
    )
    alert.setTitle(title)
    layout?.addView(edittext)
    alert.setView(layout)
    alert.setPositiveButton(
        context.getString(R.string.dialog_note_save),
        DialogInterface.OnClickListener {
                dialog, which ->
            run {
                note = edittext.text.toString()
            }
        })
    alert.setNegativeButton(
        context.getString(R.string.dialog_note_cancel),
        DialogInterface.OnClickListener {
                dialog, which ->
            run {
                note = ""
                dialog.dismiss()
            }
        })
    alert.show()

    return note
}

fun View.hideKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}