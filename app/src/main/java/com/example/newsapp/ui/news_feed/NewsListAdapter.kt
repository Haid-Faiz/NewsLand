package com.example.newsapp.ui.news_feed

import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.libnews.models.Article
import com.example.newsapp.databinding.NewsListItemBinding
import com.example.newsapp.utils.formatDate
import java.text.SimpleDateFormat
import java.util.*

class NewsListAdapter : ListAdapter<Article, NewsListAdapter.ViewHolder>(NewsListCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = getItem(position)
        holder.binding.title.text = article.title
        holder.binding.description.text = article.description
        holder.binding.sourceName.text = article.source.name
        holder.binding.articleImageView.load(article.urlToImage)
        holder.binding.timeStamp.formatDate(article)
        holder.setUpListeners(article)
    }

    class ViewHolder(val binding: NewsListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setUpListeners(article: Article) {
            // Click listener for chrome custom tabs
            binding.root.apply {
                setOnClickListener {
                    val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
//                    builder.setToolbarColor(
//                        ContextCompat.getColor(
//                            context,
//                            R.color.primary_background
//                        )
//                    )
                    builder.setShowTitle(true)
                    val customTabsIntent = builder.build()
                    customTabsIntent.launchUrl(context, Uri.parse(article.url))
                }
            }

            // Click listener for bookmark button
            binding.bookmarkButton.setOnClickListener {

            }

            // Click listener for share button
            binding.shareButton.apply {
                setOnClickListener {
                    val shareIntent = Intent(Intent.ACTION_SEND).setType("text/plain")
                    shareIntent.putExtra(Intent.EXTRA_TEXT, article.url)
                    val chooser: Intent = Intent.createChooser(shareIntent, "Share this using...")
                    context.startActivity(chooser)
                }
            }
        }
    }

    private class NewsListCallback : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem.equals(newItem)
    }
}