package com.example.newsapp.ui.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libnews.models.Article
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsListBinding
import com.example.newsapp.ui.feed.NewsFeedViewModel
import com.example.newsapp.ui.feed.NewsListAdapter
import com.example.newsapp.utils.Util
import dagger.hilt.android.AndroidEntryPoint

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
        _binding!!.statusMsgImg.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_nav_bookmark_24))
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()

        newsFeedViewModel.getAllNewsList().observe(viewLifecycleOwner) {
            it.isEmpty().also {  isEmpty ->
                _binding!!.statusBox.isVisible = isEmpty
                _binding!!.newsListRecyclerview.isVisible = !isEmpty
            }
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