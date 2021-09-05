package com.learn.lavsam.betatmdbviewer.view

import android.view.Menu
import com.learn.lavsam.betatmdbviewer.BuildConfig
import com.learn.lavsam.betatmdbviewer.R

fun buildOptionsSelect(menu: Menu?) {
    if (BuildConfig.MY_BUILD_TYPE == "DEBUG") {
        menu?.findItem(R.id.menu_actions_item_favorites)?.setVisible(true)
    } else {
        menu?.findItem(R.id.menu_actions_item_favorites)?.setVisible(false)
    }
    if (BuildConfig.MY_BUILD_TYPE == "BETA") {
        menu?.findItem(R.id.menu_actions_item_contacts)?.setVisible(true)
    } else {
        menu?.findItem(R.id.menu_actions_item_contacts)?.setVisible(false)
    }
}