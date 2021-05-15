package com.example.newsapp.ui.news_feed

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.newsapp.R

class NewsFeedFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name", "Default")
        view.findViewById<TextView>(R.id.frag_text).text = name
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
