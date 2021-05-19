package com.example.newsapp.ui.news_feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.libnews.NewsClient
import com.example.libnews.apis.NewsApi
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.data.room.NewsDatabase
import com.example.newsapp.databinding.FragmentNewsFeedBinding
import com.example.newsapp.utils.Util
import com.example.newsapp.utils.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class NewsFeedFragment : Fragment() {

    private var _binding: FragmentNewsFeedBinding? = null
    private var name: String? = "top_headlines"
    private lateinit var newsFeedViewModel: NewsFeedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
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

        val util = Util(requireContext())
        name = arguments?.getString("news_feed", "top_headlines")
        val titleList: ArrayList<String>? = util.getTabsTitle(name)

        val newsPagerAdapter = NewsPagerAdapter(childFragmentManager)
        _binding?.viewPager?.adapter = newsPagerAdapter
        // Attaching TabLayout with ViewPager2
        TabLayoutMediator(_binding!!.tabLayout, _binding!!.viewPager) { tab, position ->
            // Setting title to the tabs of TabLayout
            tab.text = titleList?.get(position)
        }.attach()

        newsFeedViewModel.tabPosition.value = 0
        _binding!!.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.d("tabPosNewsFeed", "onViewCreated: ${tab?.position}")
                newsFeedViewModel.tabPosition.value = tab?.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class NewsPagerAdapter(fm: FragmentManager) : FragmentStateAdapter(fm, lifecycle) {

        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment {
            val fragment by lazy { NewsListFragment() }
            fragment.arguments = Bundle().apply {
                putString("news", name)
            }
            return fragment
        }
    }
}
