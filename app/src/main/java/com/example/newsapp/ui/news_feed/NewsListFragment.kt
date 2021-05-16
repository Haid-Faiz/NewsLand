package com.example.newsapp.ui.news_feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapp.Utils
import com.example.newsapp.databinding.FragmentNewsListBinding

class NewsListFragment : Fragment() {

    private var _binding : FragmentNewsListBinding? = null
//    private lateinit var newsListAdapter: NewsListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val news = arguments?.getString("news", "top_headlines")!!
        val tabPosition: Int = arguments?.getInt("tab_position", 0)!!
        val endPoint: String? = Utils.getTabsTitle(news)?.get(tabPosition)

        Log.d("check", "onViewCreated: newsArg-> $news / tabPosition: $tabPosition / endPoint: $endPoint")

//        when(news) {
//            "top_headlines" -> // TODO viewModel.getCountryNews(endPoint)
//            "category" -> // TODO viewModel.getCategoryNews(endPoint)
//            "sources" -> // TODO viewModel.getSourcesNews(endPoint)
//        }
    }

}