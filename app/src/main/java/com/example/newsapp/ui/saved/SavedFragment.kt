package com.example.newsapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libnews.NewsClient
import com.example.libnews.apis.NewsApi
import com.example.libnews.models.Article
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.data.room.NewsDatabase
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.ui.feed.NewsFeedViewModel
import com.example.newsapp.ui.feed.NewsListAdapter
import com.example.newsapp.utils.Util
import com.example.newsapp.utils.ViewModelFactory

class SavedFragment : Fragment() {

    private var _binding: FragmentNewsListBinding? = null
    private lateinit var newsFeedViewModel: NewsFeedViewModel
    private lateinit var newsListAdapter: NewsListAdapter

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

        val factory = ViewModelFactory(
            NewsRepo(
                NewsClient.buildApi(NewsApi::class.java),
                NewsDatabase.invoke(requireContext())
            )
        )
        newsFeedViewModel = ViewModelProvider(this, factory).get(NewsFeedViewModel::class.java)
        setUpRecyclerView()

        newsFeedViewModel.getAllNewsList().observe(viewLifecycleOwner) {
            val newList: ArrayList<Article> = Util.toArticleList(it)
            newsListAdapter.submitList(newList)
        }
    }


    private fun setUpRecyclerView() {
        _binding!!.newsListRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        newsListAdapter = NewsListAdapter(true)
        newsListAdapter.setOnItemDeleteListener {
            newsFeedViewModel.delete(it)
        }
        _binding!!.newsListRecyclerview.adapter = newsListAdapter
    }
}