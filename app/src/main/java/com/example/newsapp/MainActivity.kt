package com.example.newsapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.newsapp.Adapter.MyAdapter
import com.example.newsapp.Adapter.NewsItemClicked
import com.example.newsapp.Mdodel.NewsData
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    lateinit var mAdapter: MyAdapter
    val MY_PERMISSION_CODE: Int = 12

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        recycler_view.layoutManager = linearLayoutManager

        fetchData()
        var newsItemClicked = object : NewsItemClicked {
            override fun onItemClick(newsUrl: String) {
                // TODO: handle click listener
                var builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
                builder.setToolbarColor(ContextCompat.getColor(this@MainActivity, R.color.blackColor))
                builder.setShowTitle(true)
                var customTabsIntent = builder.build()
                customTabsIntent.launchUrl(this@MainActivity, Uri.parse(newsUrl))
            }

            override fun onShareClick(newsUrl: String) {
                val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain")
                shareIntent.putExtra(Intent.EXTRA_TEXT, newsUrl)
                val chooser: Intent = Intent.createChooser(shareIntent, "Share it with...")
                startActivity(chooser)
            }
        }

        mAdapter = MyAdapter(newsItemClicked)
        recycler_view.adapter = mAdapter
    }

    fun fetchData() {
        progress_bar.visibility = View.VISIBLE
        val newsURL =
            "https://newsapi.org/v2/top-headlines?country=in&apiKey=730a60dec330429c8fc1a2d3eeec28fd"

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
                progress_bar.visibility = View.GONE
                mAdapter.updateNews(newsList)
            },
//           Response.ErrorListener {
            {
            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

//    override fun onStart() {
//        super.onStart()
//
//        if (checkPermission()) {
//            setUpRecyclerView()
//        }
//    }
//
//    fun checkPermission(): Boolean {
//
//        var internetPermission =
//            ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
//
////        val sampleArray = arrayOf(4,6,4,7,8,5,4)
//        val permissionList = ArrayList<String>()
//
//        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.INTERNET)
//        }
//
//        if (permissionList.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), MY_PERMISSION_CODE)
//            return false
//        } else
//            return true
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == MY_PERMISSION_CODE) {
//
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                setUpRecyclerView()
//            }else{
//                var alertDialog = AlertDialog.Builder(this)
//                    .setCancelable(false)
//                    .setTitle("Permission Required")
//                    .setMessage("Internet permission is needed")
//                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
//                        checkPermission()
//                        dialogInterface.dismiss()
//                    })
//            }
//        }
//    }
}