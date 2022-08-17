package com.absut.jetquotes.ui.quote

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.absut.jetquotes.databinding.QuoteListItemBinding
import com.absut.jetquotes.model.Quote

class QuoteAdapter(private val onItemClicked: (Quote) -> Unit) :
    PagingDataAdapter<Quote, QuoteAdapter.QuotesViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        return QuotesViewHolder(
            QuoteListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val current = getItem(position)

        if (current != null) {
            holder.itemView.setOnClickListener {
                onItemClicked(current)
            }
            holder.bind(current)
        }
    }

   inner class QuotesViewHolder(private val binding: QuoteListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quote: Quote) {
           binding.apply {
               txtQuote.text = quote.content
               txtAuthor.text = quote.author
           }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return (oldItem.id == newItem.id)
            }

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
                return oldItem == newItem
            }
        }
    }
}
