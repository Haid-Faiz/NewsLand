package com.example.newsapp.ui.news_feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libnews.NewsClient
import com.example.libnews.apis.NewsApi
import com.example.libnews.models.Article
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.utils.Util
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.ui.Resource
import com.example.newsapp.utils.ViewModelFactory
import com.example.newsapp.utils.handleApiError
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private lateinit var newsFeedViewModel: NewsFeedViewModel
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

        val factory = ViewModelFactory(NewsRepo(NewsClient.buildApi<NewsApi>(NewsApi::class.java)))
        newsFeedViewModel = ViewModelProvider(
            requireParentFragment(),
            factory
        ).get(NewsFeedViewModel::class.java)

        setUpRecyclerView()

        val util = Util(requireContext())
        val news = arguments?.getString("news", "top_headlines")!!
        val list = util.getTabsTitle(news)

        newsFeedViewModel.tabPosition.observe(viewLifecycleOwner) { position ->
//            String.format("%.1f", floatNum)

            Log.d("tabState", "observe:  NewsName: ${list?.get(position)}  // Pos: $position")

            when (news) {
                "top_headlines" -> newsFeedViewModel.getNewsByCountry(
                    util.toEnumCountry(
                        list?.get(position)
                    )
                )

                "category" -> newsFeedViewModel.getNewsByCategory(
                    util.toEnumCategory(
                        list?.get(position)
                    )
                )

                "sources" -> newsFeedViewModel.getNewsBySources(util.toEnumSource(list?.get(position)))
            }
        }

        newsFeedViewModel.article.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Failure -> {
                    handleApiError(it)
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = true
                    }
                }
                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    newsListAdapter.submitList(it.value.articles)
                    _binding!!.newsListRecyclerview.adapter = newsListAdapter
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = true
                    }
                }
                Resource.Loading -> {
                    _binding!!.apply {
                        newsListRecyclerview.isVisible = false
                    }
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        _binding!!.newsListRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        newsListAdapter = NewsListAdapter()
        _binding!!.newsListRecyclerview.adapter = newsListAdapter
    }
}