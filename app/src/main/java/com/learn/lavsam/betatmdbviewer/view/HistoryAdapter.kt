package com.learn.lavsam.betatmdbviewer.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.learn.lavsam.betatmdbviewer.data.MovieDetail
import com.learn.lavsam.betatmdbviewer.databinding.HistoryRecyclerItemBinding
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var data: List<MovieDetail> = arrayListOf()

    fun setData(data: List<MovieDetail>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.HistoryViewHolder {
        val binding = HistoryRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryAdapter.HistoryViewHolder, position: Int) {
        holder.bind(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class HistoryViewHolder(private val binding: HistoryRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(data: MovieDetail) {
                binding.apply {
                    movieHistoryTitle.text = data.title
                    movieReleaseDate.text = data.release_date
                    movieLookTime.text = data.look_time
                    movieRating.text = data.vote_average.toString()
                    movieHistoryNote.text = data.note
                }
            }

    }
}