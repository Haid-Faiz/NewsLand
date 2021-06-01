package com.example.newsapp.ui.feed

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.databinding.FragmentNewsFeedBinding
import com.example.newsapp.utils.Util
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFeedFragment() : Fragment() {

    private var _binding: FragmentNewsFeedBinding? = null
    private var name: String? = "top_headlines"
    private val newsFeedViewModel: NewsFeedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val util = Util(requireContext())
        name = arguments?.getString("news_feed", "top_headlines")
        val titleList: ArrayList<String>? = util.getTabsTitle(name)

        val newsPagerAdapter = NewsPagerAdapter(childFragmentManager)
//        _binding?.viewPager?.offscreenPageLimit = 1
        _binding?.viewPager?.adapter = newsPagerAdapter
        // Attaching TabLayout with ViewPager2
        TabLayoutMediator(_binding!!.tabLayout, _binding!!.viewPager) { tab, position ->
            // Setting title to the tabs of TabLayout
            tab.text = titleList?.get(position)
        }.attach()
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
                putInt("tab_position", position)
            }
            return fragment
        }
    }
}