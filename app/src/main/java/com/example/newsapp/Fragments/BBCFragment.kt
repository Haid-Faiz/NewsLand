package com.example.newsapp.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
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
import kotlinx.android.synthetic.main.fragment_b_b_c.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray

class BBCFragment : Fragment() {

    lateinit var mAdapter: MyAdapter
    lateinit var mContext: Context
    lateinit var mProgress: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b_b_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mProgress = view.findViewById(R.id.progress_bar_bbc)
        setUpRecyclerView()
    }

    fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(mContext)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recycler_view_bbc.layoutManager = linearLayoutManager

        fetchData()

        var newsItemClicked = object : NewsItemClicked {
            override fun onItemClick(newsUrl: String) {
                // TODO: handle click listener
                var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.blackColor
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
        recycler_view_bbc.adapter = mAdapter
    }

    fun fetchData() {
        mProgress.visibility = View.VISIBLE
        val newsURL =
            "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=730a60dec330429c8fc1a2d3eeec28fd"

        val jsonObjectRequest = JsonObjectRequest(
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

                    var news = NewsData(
                        jsonObject.getString("title"),
                        jsonObject.getString("description"),
                        jsonObjectSource.getString("name"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage"),
                        jsonObject.getString("publishedAt")
                    )
                    newsList.add(news)
                }
                mProgress.visibility = View.GONE
                mAdapter.updateNews(newsList)
                StyleableToast.makeText(mContext, "Showing BBC news",Toast.LENGTH_SHORT, R.style.CustomToast).show()
            },
//           Response.ErrorListener {
            {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
            }
        )
        MySingleton.getInstance(mContext).addToRequestQueue(jsonObjectRequest)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
}