package com.absut.jetquotes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.absut.jetquotes.R
import com.absut.jetquotes.databinding.LoaderItemBinding
import com.google.android.material.progressindicator.CircularProgressIndicator

class LoaderAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(
            LoaderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


    inner class LoaderViewHolder(private val binding: LoaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressCircular.isVisible = loadState is LoadState.Loading
                txtTitle.isVisible = loadState !is LoadState.Loading
                btRetry.isVisible = loadState !is LoadState.Loading
            }
        }
    }


}