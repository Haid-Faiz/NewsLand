package com.example.newsapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.data.paging.PagingErrorAdapter
import com.example.newsapp.utils.Util
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.utils.handleExceptions
import com.example.newsapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

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
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        val util = Util(requireContext())
        val news = arguments?.getString("news", "top_headlines")!!
        val list = util.getTabsTitle(news)

        arguments?.getInt("tab_position")?.let { position ->

            Log.d("tabPosition", "onViewCreated: $news   ////  $position")

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
            newsFeedViewModel.insert(it)
            requireView().showSnackBar("Successfully bookmarked")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}