package com.example.newsapp.ui.news_feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libnews.models.Article
import com.example.newsapp.utils.Util
import com.example.newsapp.databinding.FragmentNewsListBinding

class NewsListFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null

    //    private lateinit var newsListAdapter: NewsListAdapter
    private val newsFeedViewModel by activityViewModels<NewsFeedViewModel>()
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

        _binding!!.newsListRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        newsListAdapter = NewsListAdapter()

        val util = Util(requireContext())
        val news = arguments?.getString("news", "top_headlines")!!

        newsFeedViewModel.tabPosition.observe(requireActivity()) { position ->

            Log.d("tabPosNewsList0", "onViewCreated: $position")

            when (news) {
                "top_headlines" -> newsFeedViewModel.getNewsByCountry(
                    util.toEnumCountry(util.getTabsTitle(news)?.get(position))
                )
                "category" -> newsFeedViewModel.getNewsByCategory(
                    util.toEnumCategory(util.getTabsTitle(news)?.get(position))
                )
                "sources" -> newsFeedViewModel.getNewsBySources(
                    util.toEnumSource(util.getTabsTitle(news)?.get(position))
                )
            }
        }

        newsFeedViewModel.article.observe(viewLifecycleOwner) { it: List<Article>? ->

            Log.d("check", "onViewCreated: $it")
            newsListAdapter.submitList(it)
            _binding!!.newsListRecyclerview.adapter = newsListAdapter
        }
    }

}