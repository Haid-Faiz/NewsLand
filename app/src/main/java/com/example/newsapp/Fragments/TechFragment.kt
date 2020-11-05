package com.example.newsapp.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.newsapp.Adapter.MyAdapter
import com.example.newsapp.Adapter.NewsItemClicked
import com.example.newsapp.Mdodel.NewsData
import com.example.newsapp.MySingleton
import com.example.newsapp.R
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_tech.*
import org.json.JSONArray

class TechFragment : Fragment() {

    lateinit var mAdapter: MyAdapter
    lateinit var mContext: Context
//    lateinit var mProgress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tech, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mProgress = view.findViewById(R.id.progress_bar_tech)
        setUpRecyclerView()
//        Toast.makeText(mContext, "onViewCreated called Tech", Toast.LENGTH_SHORT).show()
    }

    fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recycler_view_tech.layoutManager = linearLayoutManager

        fetchData()

        var newsItemClicked = object : NewsItemClicked {
            override fun onItemClick(newsUrl: String) {
                // TODO: handle click listener
                var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.primary_background
                    )
                )
                builder.setShowTitle(true)
                var customTabsIntent = builder.build()
                customTabsIntent.launchUrl(mContext, Uri.parse(newsUrl))
            }

            override fun onShareClick(newsUrl: String) {
                val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_TEXT, newsUrl)
                val chooser: Intent = Intent.createChooser(shareIntent, "Share it with...")
                startActivity(chooser)
            }
        }

        mAdapter = MyAdapter(newsItemClicked)
        recycler_view_tech.adapter = mAdapter
    }

    @SuppressLint("NewApi")
    fun fetchData() {
//        mProgress.visibility = View.VISIBLE
        val newsURL =
            "https://newsapi.org/v2/top-headlines?country=in&category=technology&apiKey=730a60dec330429c8fc1a2d3eeec28fd"

        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            newsURL,
            null,
//           Response.Listener {
            {
                val newsArray: JSONArray = it.getJSONArray("articles")
                val newsList = ArrayList<NewsData>()
                for (i in 0 until newsArray.length()) {
                    val jsonObject = newsArray.getJSONObject(i)
                    val jsonObjectSource = jsonObject.getJSONObject("source")

                    // time from API call
                    val timeFromAPI = jsonObject.getString("publishedAt")
                    // here we are Using Incoming time format i.e.  yyyy-MM-dd'T'HH:mm:ss'Z'  for ex. 2020-10-25T08:13:17Z
                    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    // this will give something like this:  1603593272000
                    val time = simpleDateFormat.parse(timeFromAPI).time
                    // getting system current time
                    val now = System.currentTimeMillis()
                    // this wil give difference in string with ago added
                    val timeAgo =
                        DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

                    val news = NewsData(
                        jsonObject.getString("title"),
                        jsonObject.getString("description"),
                        jsonObjectSource.getString("name"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage"),
                        timeAgo.toString()
                    )
                    newsList.add(news)
                }
//                mProgress.visibility = View.GONE
                mAdapter.isShimming = false
                mAdapter.updateNews(newsList)
                StyleableToast.makeText(
                    mContext,
                    "Showing Technology news",
                    Toast.LENGTH_SHORT,
                    R.style.CustomToast
                ).show()
            },
//           Response.ErrorListener {
            {
//                Toast.makeText(mContext, it.toString(), Toast.LENGTH_SHORT).show()
                mAdapter.isShimming = false
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}