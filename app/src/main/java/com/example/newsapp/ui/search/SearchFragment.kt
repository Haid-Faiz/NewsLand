package com.example.newsapp.ui.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libnews.NewsClient
import com.example.libnews.apis.NewsApi
import com.example.newsapp.R
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.data.room.NewsDatabase
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.ui.Resource
import com.example.newsapp.ui.feed.NewsFeedViewModel
import com.example.newsapp.ui.feed.NewsListAdapter
import com.example.newsapp.utils.Constants.NEWS_SEARCH_TIME_DELAY
import com.example.newsapp.utils.ViewModelFactory
import com.example.newsapp.utils.handleApiError
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private lateinit var newsFeedViewModel: NewsFeedViewModel
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
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        _binding!!.statusMessageText.text = "Search any news that you are looking for !"
        _binding!!.statusMsgImg.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_nav_search_24))
        _binding!!.statusBox.isVisible = true
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory = ViewModelFactory(
            NewsRepo(
//                requireContext().applicationContext,
                NewsClient.buildApi(NewsApi::class.java),
                NewsDatabase.invoke(requireContext())
            )
        )
        newsFeedViewModel = ViewModelProvider(this, factory).get(NewsFeedViewModel::class.java)
        setUpRecyclerView()

        newsFeedViewModel.searchArticle.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Failure -> {
                    handleApiError(it)
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = true
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                }
                is Resource.Success -> {
//                    _binding!!.statusBox.isVisible = it.value.articles.isEmpty()
                    newsListAdapter.submitList(it.value.articles)
                    _binding!!.newsListRecyclerview.adapter = newsListAdapter
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = true
                        shimmerProgress.stopShimmer()
                        shimmerProgress.isVisible = false
                    }
                }
                Resource.Loading -> {
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = false
                        shimmerProgress.startShimmer()
                        shimmerProgress.isVisible = true
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        _binding!!.newsListRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        newsListAdapter = NewsListAdapter()
    }

    private fun performSearch(query: String) {
        job?.cancel()
        job = MainScope().launch {
            delay(NEWS_SEARCH_TIME_DELAY)
            newsFeedViewModel.searchNews(query)
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
                        newText?.let { if (it.isNotEmpty()) performSearch(newText) }
                        return true
                    }
                })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}