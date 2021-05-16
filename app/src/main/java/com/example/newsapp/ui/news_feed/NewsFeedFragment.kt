package com.example.newsapp.ui.news_feed

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapp.R
import com.example.newsapp.Utils
import com.example.newsapp.databinding.FragmentNewsFeedBinding
import com.google.android.material.tabs.TabLayoutMediator

class NewsFeedFragment : Fragment() {

    private var _binding: FragmentNewsFeedBinding? = null
    private var name: String? = "top_headlines"
    private var tabPosition: Int = 0

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

        name = arguments?.getString("news_feed", "top_headlines")
        view.findViewById<TextView>(R.id.frag_text).text = name
        val titleList: ArrayList<String>? = Utils.getTabsTitle(name)

        val newsPagerAdapter = NewsPagerAdapter(childFragmentManager)
        _binding?.viewPager?.adapter = newsPagerAdapter

        // Attaching TabLayout with ViewPager2
        TabLayoutMediator(_binding!!.tabLayout, _binding!!.viewPager) { tab, position ->
            // Setting title to the tabs of TabLayout
            tab.text = titleList?.get(position)
//            tabPosition = position
        }.attach()

        tabPosition = _binding!!.tabLayout.selectedTabPosition
        Log.d("tabPos", "onViewCreated: $tabPosition")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    inner class NewsPagerAdapter(fm: FragmentManager) : FragmentStateAdapter(fm, lifecycle) {

        override fun getItemCount(): Int = 5

        override fun createFragment(position: Int): Fragment {

            val fragment = NewsListFragment()
            fragment.arguments = Bundle().apply {
                putString("news", name)
                putInt("tab_position", tabPosition)
            }
            Log.d("hello", "createFragment: $name  /// pos: $tabPosition")
            return fragment
        }
    }

}


//                    // time from API call
//                    val timeFromAPI = jsonObject.getString("publishedAt")
//                    // here we are Using Incoming time format i.e.  yyyy-MM-dd'T'HH:mm:ss'Z'  for ex. 2020-10-25T08:13:17Z
//                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//                    // this will give something like this:  1603593272000
//                    val time = simpleDateFormat.parse(timeFromAPI).time
//                    // getting system current time
//                    val now = System.currentTimeMillis()
//                    // this wil give difference in string with ago added
//                    val timeAgo =
//                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)


//
//        var newsItemClicked = object : NewsItemClicked {
//            override fun onItemClick(newsUrl: String) {
//                // TODO: handle click listener
//                var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
//                builder.setToolbarColor(
//                    ContextCompat.getColor(
//                        mContext,
//                        R.color.primary_background
//                    )
//                )
//                builder.setShowTitle(true)
//                var customTabsIntent = builder.build()
//                customTabsIntent.launchUrl(mContext, Uri.parse(newsUrl))
//            }
//
//            override fun onShareClick(newsUrl: String) {
//                val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain")
//                shareIntent.putExtra(Intent.EXTRA_TEXT, newsUrl)
//                val chooser: Intent = Intent.createChooser(shareIntent, "Share it with...")
//                startActivity(chooser)
//            }
//        }
//    }
