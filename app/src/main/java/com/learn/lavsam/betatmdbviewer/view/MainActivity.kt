package com.learn.lavsam.betatmdbviewer.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.learn.lavsam.betatmdbviewer.R
import com.learn.lavsam.betatmdbviewer.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_actions, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_actions_item_list -> {
                addFragment(MainFragment())
                return true
            }
            R.id.menu_actions_item_favorites -> {
                return true
            }
            R.id.menu_actions_item_history -> {
                addFragment(HistoryFragment())
                return true
            }
            R.id.menu_actions_item_settings -> {
                addFragment(SettingsFragment())
                return true
            }
            R.id.menu_actions_item_search_results -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}