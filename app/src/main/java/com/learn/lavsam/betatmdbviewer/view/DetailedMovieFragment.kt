package com.learn.lavsam.betatmdbviewer.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.learn.lavsam.betatmdbviewer.BuildConfig
import com.learn.lavsam.betatmdbviewer.R
import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.databinding.FragmentDetailedMovieBinding
import com.learn.lavsam.betatmdbviewer.viewmodel.AppState
import com.learn.lavsam.betatmdbviewer.viewmodel.DetailsMovieViewModel
import com.squareup.picasso.Picasso

private const val IMAGE_SIZE = BuildConfig.IMAGE_SIZE_CONST
private const val BASE_URL = BuildConfig.BASE_URL_CONST
private const val VISIBILITY_GONE = View.GONE
private const val VISIBILITY_VISIBLE = View.VISIBLE

class DetailedMovieFragment : Fragment() {

    private lateinit var binding: FragmentDetailedMovieBinding
    private lateinit var movieBundle: MovieDetail

    private val viewModel: DetailsMovieViewModel by lazy {
        ViewModelProvider(this).get(DetailsMovieViewModel::class.java)
    }

    companion object {
        const val BUNDLE_EXTRA = "movie"

        fun newInstance(bundle: Bundle): DetailedMovieFragment {
            val fragment = DetailedMovieFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDetailedMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieBundle = arguments?.getParcelable<MovieDetail>(BUNDLE_EXTRA) ?: MovieDetail()
        viewModel.detailsLiveData.observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getMovieFromRemoteSource(movieBundle.id)

        binding.enterNote.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                movieBundle.note = view?.showPostDialog(getString(R.string.header_dialog_note))
                saveMovie(movieBundle)
            }
        })
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                detailedMovieView.visibility = VISIBILITY_VISIBLE
                detailedLoadingLayout.visibility = VISIBILITY_GONE
                setMovie(appState.movieData.first())

            }
            is AppState.Loading -> {
                detailedMovieView.visibility = VISIBILITY_GONE
                detailedLoadingLayout.visibility = VISIBILITY_VISIBLE
            }
            is AppState.Error -> {
                detailedMovieView.visibility = VISIBILITY_VISIBLE
                detailedLoadingLayout.visibility = VISIBILITY_GONE
                detailedMovieView.showSnackBar(getString(R.string.error_appstate),
                    getString(R.string.reload_appstate),
                    { viewModel.getMovieFromRemoteSource(movieBundle.id) })
            }
        }
    }

    private fun saveMovie(movieDetail: MovieDetail) {
        viewModel.saveMovieToDB(movieDetail)
    }

    private fun setMovie(movieDetail: MovieDetail) {
        val id = movieBundle.id
        with(binding) {
            textViewTitle.text = movieDetail.title
            textViewPlot.text = movieDetail.overview
            textViewReleased.text =
                getString(R.string.released) + movieDetail.release_date.toString()
            textViewRating.text = getString(R.string.rating) + movieDetail.vote_average.toString()
            textViewRuntime.text =
                getString(R.string.runtimelabel) + movieDetail.runtime.toString() + " min"
            textViewType.text = getString(R.string.typeMovie)
            textViewYear.text = movieDetail.release_date?.substring(0, 4) ?: ""
            saveMovie(movieDetail)
        }
        Picasso
            .get()
            .load(BASE_URL + IMAGE_SIZE + movieDetail.poster_path)
            .into(binding.imageViewPoster)

        Picasso
            .get()
            .load(BASE_URL + IMAGE_SIZE + movieDetail.backdrop_path)
            .into(binding.imageViewBackgroundPoster)
    }
}
