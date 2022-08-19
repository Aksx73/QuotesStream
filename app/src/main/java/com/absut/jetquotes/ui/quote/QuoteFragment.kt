package com.absut.jetquotes.ui.quote

import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.absut.jetquotes.R
import com.absut.jetquotes.databinding.FragmentQuotesBinding
import com.absut.jetquotes.ui.viewmodel.QuoteViewModel
import com.absut.jetquotes.ui.asMergedLoadStates
import kotlinx.coroutines.flow.*

class QuoteFragment : Fragment(R.layout.fragment_quotes), MenuProvider {

    private var _binding: FragmentQuotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var quoteAdapter: QuoteAdapter
    private val viewModel by activityViewModels<QuoteViewModel>()

    private lateinit var menuTheme: MenuItem

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentQuotesBinding.bind(view)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        initViews()
        initSwipeToRefresh()

        binding.fab.setOnClickListener {
            binding.recyclerView.scrollToPosition(0)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                //todo
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //todo
            }
        })


        /* quoteAdapter.addLoadStateListener { loadState ->
             binding.apply {
                 // swipeRefresh.isRefreshing = loadState.mediator?.refresh is LoadState.Loading
                 // recyclerView.isVisible = loadState.mediator?.refresh is LoadState.NotLoading
                 emptyView.isVisible = loadState.mediator?.refresh is LoadState.Error

                 // empty view

                 if (loadState.mediator?.refresh is LoadState.NotLoading &&
                     loadState.append.endOfPaginationReached &&
                     quoteAdapter.itemCount < 1
                 ) {
                     recyclerView.isVisible = false
                     textViewEmpty.isVisible = true
                 } else {
                     textViewEmpty.isVisible = false
                 } *

             }
         }*/


    }


    private fun initViews() {
        quoteAdapter = QuoteAdapter { quote ->
            val action = QuoteFragmentDirections.actionQuoteFragmentToQuoteDetailFragment(quote)
            findNavController().navigate(action)
        }

        binding.recyclerView.adapter = quoteAdapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter { quoteAdapter.retry() },
            footer = LoaderAdapter { quoteAdapter.retry() }
        )

        /* binding.btRetry.setOnClickListener{
             quoteAdapter.retry()
         }*/

        lifecycleScope.launchWhenCreated {
            quoteAdapter.loadStateFlow.collect { loadStates ->
                if (_binding != null) {
                    binding.swipeRefresh.isRefreshing =
                        loadStates.mediator?.refresh is LoadState.Loading
                }
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.quotes.collectLatest {
                quoteAdapter.submitData(it)
            }
        }

        lifecycleScope.launchWhenCreated {
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
                .collect {
                    if (_binding != null) {
                        binding.recyclerView.scrollToPosition(0)
                    }
                }
        }


    }


    private fun initSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener { quoteAdapter.refresh() }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_main, menu)
        menuTheme = menu.findItem(R.id.action_theme)
        setUpMenuThemeIcon(viewModel.isDark)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_favourite -> {
                findNavController().navigate(R.id.action_quoteFragment_to_favoriteFragment)
                true
            }
            R.id.action_theme -> {
                // Get new mode.
                val mode =
                    if ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_NO) {
                        viewModel.isDark = true
                        AppCompatDelegate.MODE_NIGHT_YES
                    } else {
                        viewModel.isDark = false
                        AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
                    }
                // Change UI Mode
                AppCompatDelegate.setDefaultNightMode(mode)
                setUpMenuThemeIcon(viewModel.isDark)
                true
            }
            else -> false
        }
    }

    private fun setUpMenuThemeIcon(isDark: Boolean) {
        menuTheme.icon = if (isDark) ContextCompat.getDrawable(
            requireActivity(),
            R.drawable.ic_light_mode_24px
        ) else ContextCompat.getDrawable(requireActivity(), R.drawable.ic_dark_mode_24dp)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}