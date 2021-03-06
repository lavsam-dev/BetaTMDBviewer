package com.learn.lavsam.betatmdbviewer.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.learn.lavsam.betatmdbviewer.R
import com.learn.lavsam.betatmdbviewer.databinding.HistoryFragmentBinding
import com.learn.lavsam.betatmdbviewer.viewmodel.AppState
import com.learn.lavsam.betatmdbviewer.viewmodel.HistoryViewModel
import kotlinx.android.synthetic.main.history_fragment.*

private const val VISIBILITY_GONE = View.GONE
private const val VISIBILITY_VISIBLE = View.VISIBLE

class HistoryFragment : Fragment() {

    private var _binding: HistoryFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by lazy {
        ViewModelProvider(this).get(HistoryViewModel::class.java)
    }
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HistoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerView.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getAllHistory()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                binding.historyFragmentRecyclerView.visibility = VISIBILITY_VISIBLE
                binding.loadingLayout.visibility = View.GONE
                adapter.setData(appState.movieData)
            }
            is AppState.Loading -> {
                binding.historyFragmentRecyclerView.visibility = VISIBILITY_GONE
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.historyFragmentRecyclerView.visibility = VISIBILITY_VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.historyFragmentRecyclerView.showSnackBar(
                    getString(R.string.error_appstate),
                    getString(R.string.reload_appstate),
                    {
                        viewModel.getAllHistory()
                    })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}