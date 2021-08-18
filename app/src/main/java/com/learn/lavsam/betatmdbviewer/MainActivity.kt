package com.learn.lavsam.betatmdbviewer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.learn.lavsam.betatmdbviewer.R
import com.learn.lavsam.betatmdbviewer.databinding.MainActivityBinding
import com.learn.lavsam.betatmdbviewer.view.MainFragment

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
}