//package com.example.newsapp.Adapter
//
//import android.graphics.drawable.Drawable
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageButton
//import android.widget.ImageView
//import android.widget.ProgressBar
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.newsapp.Mdodel.NewsData
//import com.example.newsapp.R
//import com.facebook.shimmer.ShimmerFrameLayout
//
//class MyAdapter(val listener: NewsItemClicked) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
//
//    val newsList: ArrayList<NewsData> = ArrayList()
//    var isShimming = true
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
//        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
//
//
//        if (isShimming) {
//            holder.shimmerLay.startShimmer()
//        } else {
//            holder.shimmerLay.stopShimmer()
//            holder.shimmerLay.setShimmer(null)
//
//            val currentNewsData = newsList[position]
//            holder.titleView.background = null
//            holder.titleView.text = currentNewsData.title
//            holder.descriptionView.background = null
//            holder.descriptionView.text = currentNewsData.description
//            holder.authorView.background = null
//            holder.authorView.text = currentNewsData.author
//            holder.timeStampview.background = null
//            holder.timeStampview.text = currentNewsData.timeStamp
//
//            holder.progressBarImage.visibility = View.VISIBLE
////            holder.shimmerImage.startShimmer()
//            Glide.with(holder.imageView.context).load(currentNewsData.imageUrl)
//                .listener(object : RequestListener<Drawable> {
//                    override fun onLoadFailed(
//                        e: GlideException?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        holder.progressBarImage.visibility = View.GONE
////                    holder.shimmerImage.stopShimmer()
////                    holder.shimmerImage.setShimmer(null)
//                        return false
//                    }
//
//                    override fun onResourceReady(
//                        resource: Drawable?,
//                        model: Any?,
//                        target: Target<Drawable>?,
//                        dataSource: DataSource?,
//                        isFirstResource: Boolean
//                    ): Boolean {
//                        holder.progressBarImage.visibility = View.GONE
////                    holder.shimmerImage.stopShimmer()
////                    holder.shimmerImage.setShimmer(null)
//                        return false
//                    }
//
//                })
//                .into(holder.imageView)
//
//            holder.itemView.setOnClickListener {
//                listener.onItemClick(currentNewsData.newsUrl)
//            }
//
//            holder.shareButton.setOnClickListener {
//                listener.onShareClick(currentNewsData.newsUrl)
//            }
//
//        }
//
//    }
//
//    override fun getItemCount(): Int {
//        return if (isShimming)
//            2                                // in Kotlin if else return their last expresssion
//        else
//            newsList.size
//    }
//
//    fun updateNews(updatedNews: ArrayList<NewsData>) {
//        newsList.clear()
//        newsList.addAll(updatedNews)
//        notifyDataSetChanged()
//    }
//
//    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val titleView = itemView.findViewById<TextView>(R.id.title)
//        val descriptionView = itemView.findViewById<TextView>(R.id.description)
//        val authorView = itemView.findViewById<TextView>(R.id.author_name)
//        val timeStampview = itemView.findViewById<TextView>(R.id.time_stamp)
//        val imageView = itemView.findViewById<ImageView>(R.id.imageView)
//        val progressBarImage = itemView.findViewById<ProgressBar>(R.id.progress_bar_image)
//
//        //        val shimmerImage = itemView.findViewById<ShimmerFrameLayout>(R.id.shimmer_image)
//        val shareButton = itemView.findViewById<ImageButton>(R.id.share_button)
//        val shimmerLay = itemView.findViewById<ShimmerFrameLayout>(R.id.shimmer_layout)
//    }
//}
//
//interface NewsItemClicked {
//    fun onItemClick(item: String)
//    fun onShareClick(newsUrl: String)
//}