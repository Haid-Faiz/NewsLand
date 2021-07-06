package com.example.newsapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.utils.PagingErrorAdapter
import com.example.newsapp.utils.Util
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.utils.handleExceptions
import com.example.newsapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    //    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private val newsFeedViewModel: NewsFeedViewModel by activityViewModels()
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
        val util = Util(requireContext())
        val news = arguments?.getString("news", "top_headlines")!!
        val list = util.getTabsTitle(news)

        arguments?.getInt("tab_position")?.let { position ->
            Log.d("tabPosition", "onViewCreated-> tag: $news  | position: $position")

            lifecycleScope.launchWhenCreated {
                when (news) {
                    "top_headlines" -> newsFeedViewModel
                        .getNewsByCountry(util.toEnumCountry(list?.get(position)))
                        .collect {
                            newsListAdapter.submitData(lifecycle, it)
                        }

                    "category" -> newsFeedViewModel
                        .getNewsByCategory(util.toEnumCategory(list?.get(position)))
                        .collect {
                            newsListAdapter.submitData(lifecycle, it)
                        }

                    "sources" -> newsFeedViewModel
                        .getNewsBySources(util.toEnumSource(list?.get(position)))
                        .collect {
                            newsListAdapter.submitData(lifecycle, it)
                        }
                }
            }
        }

        _binding!!.retryButton.setOnClickListener {
            newsListAdapter.retry()
        }
    }

    private fun setUpRecyclerView() {
        _binding!!.newsListRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
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
    }
}