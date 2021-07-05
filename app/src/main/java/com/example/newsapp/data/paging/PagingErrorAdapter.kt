package com.example.newsapp.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.PageErrorItemBinding

class PagingErrorAdapter(val retry: () -> Unit) :
    LoadStateAdapter<PagingErrorAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        return ViewHolder(
            PageErrorItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) = holder.bind(loadState)

    inner class ViewHolder(private val binding: PageErrorItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(loadState: LoadState) {
            binding.apply {

                retryButton.setOnClickListener {
                    retry()
                }
                when (loadState) {
                    LoadState.Loading -> {
                        loadMsg.text = "Hang on... loading content"
                        progressBar.isVisible = true
                        retryButton.isVisible = false
                    }
//                    is LoadState.NotLoading -> TODO()
                    is LoadState.Error -> {
                        loadMsg.text = "Oops... Could not load content"
                        progressBar.isVisible = false
                        retryButton.isVisible = true
                    }
                }
            }
        }
    }
}
