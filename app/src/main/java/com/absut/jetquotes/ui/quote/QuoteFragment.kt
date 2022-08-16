package com.absut.jetquotes.ui.quote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.absut.jetquotes.databinding.FragmentQuotesBinding
import com.absut.jetquotes.ui.adapter.LoaderAdapter
import com.absut.jetquotes.ui.viewmodel.QuoteViewModel
import com.absut.jetquotes.ui.adapter.QuoteAdapter
import com.absut.jetquotes.ui.asMergedLoadStates
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.*


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class QuoteFragment : Fragment() {

    private var _binding: FragmentQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var quoteAdapter: QuoteAdapter
    private val viewModel: QuoteViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentQuotesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initSwipeToRefresh()

        binding.fab.setOnClickListener {
            binding.recyclerView.scrollToPosition(0)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
               //todo
            }
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //todo
            }
        })

    }


    private fun initViews() {
        quoteAdapter = QuoteAdapter { quote ->
            Snackbar.make(binding.recyclerView, "Quote ID: ${quote.id}", Snackbar.LENGTH_SHORT).show()
        }

        binding.recyclerView.adapter = quoteAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        lifecycleScope.launchWhenCreated {
            quoteAdapter.loadStateFlow.collect { loadStates ->
                binding.swipeRefresh.isRefreshing =
                    loadStates.mediator?.refresh is LoadState.Loading
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.quotes.collectLatest {
                quoteAdapter.submitData(it)
            }
        }

       /* lifecycleScope.launchWhenCreated {
            quoteAdapter.loadStateFlow
                // Use a state-machine to track LoadStates such that we only transition to
                // NotLoading from a RemoteMediator load if it was also presented to UI.
                .asMergedLoadStates()
                // Only emit when REFRESH changes, as we only want to react on loads replacing the
                // list.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                // Scroll to top is synchronous with UI updates, even if remote load was triggered.
                .collect { binding.recyclerView.scrollToPosition(0) }
        }*/

    }


    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { quoteAdapter.refresh() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}