package com.learn.lavsam.betatmdbviewer.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.learn.lavsam.betatmdbviewer.R
import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.databinding.MainFragmentBinding
import com.learn.lavsam.betatmdbviewer.view.SettingsFragment.Companion.IS_ADULT_SETTING
import com.learn.lavsam.betatmdbviewer.viewmodel.AppState
import com.learn.lavsam.betatmdbviewer.viewmodel.MainViewModel

private const val FIRST_PAGE = 1
private const val VISIBILITY_GONE = View.GONE
private const val VISIBILITY_VISIBLE = View.VISIBLE

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
        var isAdultMovie: Boolean = false
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(movie: MovieDetail) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.container, DetailedMovieFragment.newInstance(Bundle().apply {
                        putParcelable(DetailedMovieFragment.BUNDLE_EXTRA, movie)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readSettings()
        binding.mainFragmentRecyclerView.adapter = adapter
        viewModel.liveDataToObserve.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getMoviesListFromServer(FIRST_PAGE, isAdultMovie)
    }

    private fun readSettings() {
        activity?.let {
            isAdultMovie =
                (it.getPreferences(Context.MODE_PRIVATE).getBoolean(IS_ADULT_SETTING, false))
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.loadingLayout.visibility = VISIBILITY_GONE
                adapter.setMovie(appState.movieData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = VISIBILITY_VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = VISIBILITY_GONE
                binding.main.showSnackBar(getString(R.string.error_appstate),
                    getString(R.string.reload_appstate),
                    { viewModel.getMoviesListFromServer(FIRST_PAGE, isAdultMovie) })
            }
        }
    }

    override fun onDestroy() {
        adapter.removeListener()
        _binding = null
        super.onDestroy()
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(movie: MovieDetail)
    }
}