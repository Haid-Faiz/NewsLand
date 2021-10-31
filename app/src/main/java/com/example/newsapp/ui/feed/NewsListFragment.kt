package com.example.newsapp.ui.feed

import android.os.Bundle
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
import com.example.newsapp.utils.Constants.NEWS_TYPE
import com.example.newsapp.utils.Constants.TAB_POSITION
import com.example.newsapp.utils.Constants.TOP_HEADLINES
import com.example.newsapp.utils.PagingErrorAdapter
import com.example.newsapp.utils.handleExceptions
import com.example.newsapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsListAdapter
//    private var lastTab: Int? = null
//    private var newsType: String? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        savedInstanceState?.let {
//            lastTab = it.getInt("lastTab")
//            newsType = it.getString("lastTabType")
//        }
//    }

    @Inject
    lateinit var factory: NewsFeedViewModel.Factory
    private val viewModel: NewsFeedViewModel by viewModels {
        NewsFeedViewModel.providesFactory(
            assistedFactory = factory,
            newsType = requireArguments().getString(NEWS_TYPE, TOP_HEADLINES),
            tabPosition = requireArguments().getInt(TAB_POSITION)
        )
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        outState.putInt("lastTab", requireArguments().getInt(TAB_POSITION))
//        outState.putString("lastTabType", requireArguments().getString(NEWS_TYPE, TOP_HEADLINES))
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        binding.statusMessageText.text = resources.getString(R.string.connection_error_msg)
        binding.retryButton.isVisible = true
        binding.statusMsgImg.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_connection_error_24)
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.newsFeed?.collectLatest { adapter.submitData(lifecycle, it) }
        }
    }

    private fun setUpRecyclerView() {
        adapter = NewsListAdapter()
        binding.newsListRecyclerview.adapter = adapter.withLoadStateHeaderAndFooter(
            header = PagingErrorAdapter { adapter.retry() },
            footer = PagingErrorAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadStates ->
            when (loadStates.refresh) {
                is LoadState.NotLoading -> {
                    binding.apply {
                        newsListRecyclerview.isVisible = true
                        statusBox.isVisible = false
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                }

                LoadState.Loading -> {
                    binding.apply {
                        newsListRecyclerview.isVisible = false
                        statusBox.isVisible = false
                        shimmerProgress.startShimmer()
                        shimmerProgress.isVisible = true
                    }
                }
                is LoadState.Error -> {
                    binding.apply {
                        newsListRecyclerview.isVisible = false
                        statusBox.isVisible = true
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                    handleExceptions((loadStates.refresh as LoadState.Error).error)
                }
            }
        }
        adapter.setOnItemBookMarkListener {
            viewModel.insert(it)
            requireView().showSnackBar("Successfully bookmarked")
        }

        binding.retryButton.setOnClickListener { adapter.retry() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
