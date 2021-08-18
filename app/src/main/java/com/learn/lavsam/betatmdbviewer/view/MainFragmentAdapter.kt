package com.learn.lavsam.betatmdbviewer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.lavsam.betatmdbviewer.R
import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.databinding.MainFragmentRecyclerItemBinding
import com.squareup.picasso.Picasso

private const val BASE_URL = "https://image.tmdb.org/t/p/w500/"

class MainFragmentAdapter() :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var movieData: List<MovieDetail> = listOf()

    fun setMovie(data: List<MovieDetail>) {
        movieData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFragmentAdapter.MainViewHolder {
        val binding = MainFragmentRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainFragmentAdapter.MainViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    inner class MainViewHolder(private val binding: MainFragmentRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieDetail) = with(binding) {
            textViewPopularity.text = movie.vote_average.toString()
            textViewTitle.text = movie.title
            textViewYearOfRelease.text = movie.release_date.toString()
            imageViewPoster.setImageResource(R.drawable.ic_launcher_background)
            Picasso
                .get()
                .load(BASE_URL + movie.poster_path)
                .into(imageViewPoster)
            root
        }
    }
}
