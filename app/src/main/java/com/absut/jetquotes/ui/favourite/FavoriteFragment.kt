package com.absut.jetquotes.ui.favourite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.absut.jetquotes.R
import com.absut.jetquotes.databinding.FragmentFavoriteBinding
import com.absut.jetquotes.ui.viewmodel.QuoteViewModel

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<QuoteViewModel>()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavoriteBinding.bind(view)

        favoriteAdapter = FavoriteAdapter { quote ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToQuoteDetailFragment(quote)
            findNavController().navigate(action)
        }

        binding.recyclerViewFavorite.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = favoriteAdapter
        }

        viewModel.favoriteQuotes().observe(viewLifecycleOwner){
            favoriteAdapter.submitList(it)
            if(favoriteAdapter.itemCount == 0) binding.emptyView.visibility = View.VISIBLE
            else binding.emptyView.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}