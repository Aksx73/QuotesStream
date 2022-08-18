package com.absut.jetquotes.ui.detail

import android.content.*
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.absut.jetquotes.BuildConfig
import com.absut.jetquotes.R
import com.absut.jetquotes.databinding.FragmentQuoteDetailBinding
import com.absut.jetquotes.ui.viewmodel.QuoteViewModel
import com.google.android.material.snackbar.Snackbar
import java.io.File


class QuoteDetailFragment : Fragment(R.layout.fragment_quote_detail) {

    private var _binding: FragmentQuoteDetailBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<QuoteDetailFragmentArgs>()
    private val viewModel by activityViewModels<QuoteViewModel>()

    private var isFavorite: Boolean = false

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
                    args.quote.isFavorite = false    //changed value for in-memory quote object to reflect ui change
                } else {
                    viewModel.updateFavoriteStatus(args.quote.id, true)
                    args.quote.isFavorite = true      //changed value for in-memory quote object to reflect ui change
                }
                setFavoriteIcon()
            }

            save.setOnClickListener {
                val bitmap = getBitmapFromView(binding.quoteCard)
                val uri = createImageUri(args.quote.author)
                storeBitmap(bitmap, uri)
            }

            share.setOnClickListener {
                val intent = Intent(Intent.ACTION_SEND)
                    .setType("text/plain")
                    .putExtra(
                        Intent.EXTRA_TEXT,
                        getQuoteCopy(args.quote.content, args.quote.author)
                    )
                if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
                    startActivity(Intent.createChooser(intent, "Share using"))
                }
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


    private fun getBitmapFromView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val bg = view.background
        bg.draw(canvas)
        view.draw(canvas)
        return bitmap
    }

    private fun createImageUri(author: String): Uri {
        val image = File(
            requireActivity().applicationContext.filesDir,
            "quote_by_${author.replace(" ", "").lowercase()}"
        )
        return FileProvider.getUriForFile(
            requireActivity().applicationContext,
            "${BuildConfig.APPLICATION_ID}.fileprovider",
            image
        )
    }

    private fun storeBitmap(bitmap: Bitmap, imageUri: Uri) {
        val outputStream = requireActivity().applicationContext.contentResolver.openOutputStream(imageUri)
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        outputStream!!.close()

        Snackbar.make(binding.constraintLayout, "Quote saved", Snackbar.LENGTH_LONG)
            .setAnchorView(binding.constraintLayout)
            .setAction("Open") {
                openScreenshot(imageUri)
            }
            .show()
    }


    private fun openScreenshot(uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW)
            .setDataAndType(uri, "image/*")
            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
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
