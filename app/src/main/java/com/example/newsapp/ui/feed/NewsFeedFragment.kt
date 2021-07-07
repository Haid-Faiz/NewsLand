package com.example.newsapp.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.databinding.FragmentNewsFeedBinding
import com.example.newsapp.utils.Constants.NEWS_FEED
import com.example.newsapp.utils.Constants.NEWS_TAG
import com.example.newsapp.utils.Constants.TAB_POSITION
import com.example.newsapp.utils.Constants.TOP_HEADLINES
import com.example.newsapp.utils.Util
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFeedFragment : Fragment() {

    @Inject
    lateinit var util: Util    // Field Injection
    private var _binding: FragmentNewsFeedBinding? = null
    private var name: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsFeedBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        name = requireArguments().getString(NEWS_FEED, TOP_HEADLINES)
        val titleList: ArrayList<String>? = util.getTabsTitle(name)

        val newsPagerAdapter = NewsPagerAdapter(childFragmentManager)
        // _binding?.viewPager?.offscreenPageLimit = 1
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
                putString(NEWS_TAG, name)
                putInt(TAB_POSITION, position)
            }
            return fragment
        }
    }
}
