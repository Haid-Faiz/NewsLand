package com.example.newsapp.Adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newsapp.Mdodel.NewsData
import com.example.newsapp.R

class MyAdapter(val listener: NewsItemClicked) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    val newsList: ArrayList<NewsData> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val currentNewsData = newsList[position]
        holder.titleView.text = currentNewsData.title
        holder.descriptionView.text = currentNewsData.description
        holder.authorView.text = currentNewsData.author
        holder.timeStampview.text = currentNewsData.timeStamp

        Glide.with(holder.imageView.context).load(currentNewsData.imageUrl).into(holder.imageView)

        holder.itemView.setOnClickListener {
            listener.onItemClick(currentNewsData.newsUrl)
        }

        holder.shareButton.setOnClickListener{
            listener.onShareClick(currentNewsData.newsUrl)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun updateNews(updatedNews: ArrayList<NewsData>){
        newsList.clear()
        newsList.addAll(updatedNews)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleView = itemView.findViewById<TextView>(R.id.title)
        val descriptionView = itemView.findViewById<TextView>(R.id.description)
        val authorView = itemView.findViewById<TextView>(R.id.author_name)
        val timeStampview = itemView.findViewById<TextView>(R.id.time_stamp)
        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
        val progressBarImage = itemView.findViewById<ProgressBar>(R.id.progress_bar_image)
        val shareButton = itemView.findViewById<ImageButton>(R.id.share_button)
    }
}

interface NewsItemClicked{
    fun onItemClick(item: String)
    fun onShareClick(newsUrl: String)
}