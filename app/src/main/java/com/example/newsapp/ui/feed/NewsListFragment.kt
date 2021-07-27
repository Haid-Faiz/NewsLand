package com.example.newsapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.utils.Constants.CATEGORY
import com.example.newsapp.utils.Constants.NEWS_TAG
import com.example.newsapp.utils.Constants.SOURCES
import com.example.newsapp.utils.Constants.TAB_POSITION
import com.example.newsapp.utils.Constants.TOP_HEADLINES
import com.example.newsapp.utils.PagingErrorAdapter
import com.example.newsapp.utils.Util
import com.example.newsapp.utils.handleExceptions
import com.example.newsapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    @Inject
    lateinit var util: Util
    private var _binding: FragmentNewsListBinding? = null
    private val newsFeedViewModel: NewsFeedViewModel by viewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        _binding!!.statusMessageText.text = resources.getString(R.string.connection_error_msg)
        _binding!!.retryButton.isVisible = true
        _binding!!.statusMsgImg.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_connection_error_24
            )
        )
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        val news = requireArguments().getString(NEWS_TAG, TOP_HEADLINES)
        val list = util.getTabsTitle(news)

        requireArguments().getInt(TAB_POSITION).let { position ->

            if (!newsFeedViewModel.isRotated) {
                Log.d("orientation", "onViewCreated: called")
                when (news) {
                    TOP_HEADLINES ->
                        newsFeedViewModel
                            .getNewsByCountry(util.toEnumCountry(list?.get(position)))

                    CATEGORY ->
                        newsFeedViewModel
                            .getNewsByCategory(util.toEnumCategory(list?.get(position)))

                    SOURCES ->
                        newsFeedViewModel
                            .getNewsBySources(util.toEnumSource(list?.get(position)))
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            newsFeedViewModel.news.collectLatest {
                newsListAdapter.submitData(lifecycle, it)
            }
        }

        _binding!!.retryButton.setOnClickListener {
            newsListAdapter.retry()
        }
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
                        statusBox.isVisible = false
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                }

                LoadState.Loading -> {
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = false
                        statusBox.isVisible = false
                        shimmerProgress.startShimmer()
                        shimmerProgress.isVisible = true
                    }
                }
                is LoadState.Error -> {
                    handleExceptions((it.refresh as LoadState.Error).error)
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = false
                        statusBox.isVisible = true
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                }
            }
        }
        newsListAdapter.setOnItemBookMarkListener {
            newsFeedViewModel.insert(it)
            requireView().showSnackBar("Successfully bookmarked")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        newsFeedViewModel.isRotated = true
    }
}
