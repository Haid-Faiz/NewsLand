package com.example.newsapp.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.ui.feed.NewsListAdapter
import com.example.newsapp.utils.Constants.NEWS_SEARCH_TIME_DELAY
import com.example.newsapp.utils.PagingErrorAdapter
import com.example.newsapp.utils.handleExceptions
import com.example.newsapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var newsListAdapter: NewsListAdapter
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        _binding!!.statusMessageText.text = resources.getString(R.string.search_msg)
        _binding!!.statusMsgImg.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_nav_search_24)
        )
        _binding!!.statusBox.isVisible = true
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        newsListAdapter = NewsListAdapter()
        _binding!!.newsListRecyclerview.adapter = newsListAdapter.withLoadStateHeaderAndFooter(
            header = PagingErrorAdapter { newsListAdapter.retry() },
            footer = PagingErrorAdapter { newsListAdapter.retry() }
        )
        newsListAdapter.addLoadStateListener {
            when (it.refresh) {
                is LoadState.NotLoading -> {
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = true
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                }
                LoadState.Loading -> {
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = false
                        shimmerProgress.startShimmer()
                        shimmerProgress.isVisible = true
                    }
                }
                is LoadState.Error -> {
                    handleExceptions((it.refresh as LoadState.Error).error)
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = false
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                }
            }
        }
        newsListAdapter.setOnItemBookMarkListener {
            viewModel.insert(it)
            requireView().showSnackBar("Successfully bookmarked")
        }
    }

    private fun performSearch(query: String) {
        job?.cancel()
        job = viewLifecycleOwner.lifecycleScope.launch {
            delay(NEWS_SEARCH_TIME_DELAY)
            viewModel.searchNews(query)
            viewModel.news?.collectLatest {
                newsListAdapter.submitData(lifecycle, it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.search_action -> {
                _binding!!.statusBox.isVisible = false
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        newText?.let { if (it.isNotEmpty()) performSearch(it) }
                        return true
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        job = null
    }
}
