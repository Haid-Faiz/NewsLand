package com.example.newsapp.ui.feed

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
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.data.room.NewsDatabase
import com.example.newsapp.utils.Util
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.ui.Resource
import com.example.newsapp.utils.ViewModelFactory
import com.example.newsapp.utils.handleApiError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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

//        val factory = ViewModelFactory(
//            NewsRepo(
//                requireContext().applicationContext,
//                NewsClient.buildApi<NewsApi>(NewsApi::class.java),
//                NewsDatabase.invoke(requireContext())
//            )
//        )
//        newsFeedViewModel = ViewModelProvider(
//            requireParentFragment(),
//            factory
//        ).get(NewsFeedViewModel::class.java)

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

        var job: Job? = null

        newsFeedViewModel.article.observe(viewLifecycleOwner) {

            when (it) {
                is Resource.Failure -> {
                    handleApiError(it)
                    Toast.makeText(requireContext(), "Failed", Toast.LENGTH_SHORT).show()
                    _binding!!.apply {

                        job?.cancel()
                        job = MainScope().launch {
                            delay(1000)
                            newsListRecyclerview.isVisible = true
                            shimmerProgress.stopShimmer()
                            shimmerProgress.isVisible = false
                        }
                    }
                }

                is Resource.Success -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    newsListAdapter.submitList(it.value.articles)
                    _binding!!.newsListRecyclerview.adapter = newsListAdapter
                    _binding!!.apply {

                        job?.cancel()
                        job = MainScope().launch {
                            delay(1000)
                            newsListRecyclerview.isVisible = true
                            shimmerProgress.stopShimmer()
                            shimmerProgress.isVisible = false
                        }

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
        newsListAdapter.setOnItemBookMarkListener {
            newsFeedViewModel.insert(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}