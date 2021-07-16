package com.example.newsapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.ui.feed.NewsFeedViewModel
import com.example.newsapp.ui.feed.NewsListAdapter
import com.example.newsapp.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private val newsFeedViewModel: NewsFeedViewModel by viewModels()
    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        _binding!!.statusMessageText.text = resources.getString(R.string.no_bookmark_msg)
        _binding!!.statusMsgImg.setImageDrawable(
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_nav_bookmark_24)
        )
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        lifecycleScope.launchWhenCreated {
            newsFeedViewModel.getAllNewsList().collectLatest {
                newsListAdapter.submitData(lifecycle, it)
                newsListAdapter.loadStateFlow.collectLatest { loadStates: CombinedLoadStates ->
                    val refreshState = loadStates.source.refresh
                    val isListEmpty = (refreshState is LoadState.NotLoading
                            && loadStates.append.endOfPaginationReached
                            && newsListAdapter.itemCount == 0)

                    _binding!!.newsListRecyclerview.isGone = isListEmpty
                    _binding!!.statusBox.isGone = !isListEmpty
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        newsListAdapter = NewsListAdapter(true)
        newsListAdapter.setOnItemDeleteListener {
            newsFeedViewModel.delete(it)
            requireView().showSnackBar("Successfully deleted")
        }
        _binding!!.newsListRecyclerview.adapter = newsListAdapter
    }
}