package com.absut.jetquotes.ui.detail

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.absut.jetquotes.R
import com.absut.jetquotes.databinding.FragmentQuoteDetailBinding
import com.absut.jetquotes.databinding.FragmentQuotesBinding
import com.absut.jetquotes.ui.viewmodel.QuoteViewModel
import com.google.android.material.snackbar.Snackbar

class QuoteDetailFragment : Fragment(R.layout.fragment_quote_detail) {

    private var _binding: FragmentQuoteDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<QuoteDetailFragmentArgs>()
    private val viewModel by activityViewModels<QuoteViewModel>()

    private var isFavorite:Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuoteDetailBinding.bind(view)

        binding.apply {
            txtQuote.text = args.quote.content
            txtAuthor.text = args.quote.author

            setFavoriteIcon()

            favorite.setOnClickListener {
                if (isFavorite) {
                    viewModel.updateFavoriteStatus(args.quote.id, false)
                    args.quote.isFavorite = false
                } else {
                    viewModel.updateFavoriteStatus(args.quote.id, true)
                    args.quote.isFavorite = true
                }
                setFavoriteIcon()
               /* val resultMsg = if (isFavorite) "Added to favorite" else "Removed from favorite"
                Snackbar.make(view, resultMsg, Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.constraintLayout)
                    .show()*/
            }
            save.setOnClickListener {
                Snackbar.make(view, "Quote saved", Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.constraintLayout)
                    .show()
            }
            share.setOnClickListener {
                Snackbar.make(view, "Share clicked", Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.constraintLayout)
                    .show()
            }
            copy.setOnClickListener {
                requireActivity().copyToClipboard(
                    "quote",
                    getQuoteCopy(args.quote.content, args.quote.author)
                )

                Snackbar.make(view, "Quote copied to clipboard", Snackbar.LENGTH_SHORT)
                    .setAnchorView(binding.constraintLayout)
                    .show()

            }

        }

    }

    private fun setFavoriteIcon() {
        if (args.quote.isFavorite) {
            isFavorite = true
            binding.imgFavorite?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite_filled_24px
                )
            )
        } else {
            binding.imgFavorite?.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_favorite_24px
                )
            )
            isFavorite = false
        }
    }

    private fun Context.copyToClipboard(clipLabel: String, text: CharSequence) {
        val clipboard = getSystemService(this, ClipboardManager::class.java)
        clipboard?.setPrimaryClip(ClipData.newPlainText(clipLabel, text))
    }

    private fun getQuoteCopy(content: String, author: String): String {
        return "$content \n\n-$author"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}