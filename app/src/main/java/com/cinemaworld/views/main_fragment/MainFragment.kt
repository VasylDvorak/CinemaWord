package com.cinemaworld.views.main_fragment

import android.app.SearchManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cinemaworld.R
import com.cinemaworld.databinding.FragmentMainBinding
import com.cinemaworld.di.ConnectKoinModules.mainScreenScope
import com.cinemaworld.model.data_word_request.Result
import com.cinemaworld.model.datasource.AppState
import com.cinemaworld.simpleScan
import com.cinemaworld.views.MainViewModel
import com.cinemaworld.views.base_for_cinema.BaseFragment
import com.cinemaworld.views.description.CURRENT_RESULT
import com.cinemaworld.views.description.DescriptionFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch



class MainFragment : BaseFragment<AppState, FragmentMainBinding>(FragmentMainBinding::inflate) {

    lateinit var model: MainViewModel

    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder


    private val adapter: FilmsAdapter by lazy {
        FilmsAdapter(::onItemClick)
    }


    private fun onItemClick(result: Result) {
        result.let {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(R.id.flFragment, DescriptionFragment.newInstance(Bundle().apply {
                        putParcelable(CURRENT_RESULT, result)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupFilmsList()
        setupSwipeToRefresh()

    }


    private fun setupFilmsList() {

        // in case of loading errors this callback is called when you tap the 'Try Again' button
        val tryAgainAction: TryAgainAction = { adapter.retry() }

        val footerAdapter = DefaultLoadStateAdapter(tryAgainAction)

        // combined adapter which shows both the list of users + footer indicator when loading pages
        val adapterWithLoadState = adapter
            .withLoadStateHeaderAndFooter(header = footerAdapter, footer = footerAdapter)
        binding.apply {
            filmsRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                filmsRecyclerView.adapter = adapterWithLoadState
                (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = true
            }
            mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
                loadStateView,
                swipeRefreshLayout,
                tryAgainAction
            )
        }
        observeUsers()
        observeLoadState()
        handleScrollingToTopWhenSearching()
        handleListVisibility()
    }

    private fun observeUsers() {
        val viewModel: MainViewModel by lazy { mainScreenScope.get() }
        model = viewModel
        lifecycleScope.launch {
            model.usersFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState() {
        // you can also use adapter.addLoadStateListener
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                // main indicator in the center of the screen
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun handleScrollingToTopWhenSearching() = lifecycleScope.launch {
        // list should be scrolled to the 1st item (index = 0) if data has been reloaded:
        // (prev state = Loading, current state = NotLoading)
        getRefreshLoadStateFlow()
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.filmsRecyclerView.scrollToPosition(0)
                }
            }
    }

    private fun handleListVisibility() = lifecycleScope.launch {
        // list should be hidden if an error is displayed OR if items are being loaded after the error:
        // (current state = Error) OR (prev state = Error)
        //   OR
        // (before prev state = Error, prev state = NotLoading, current state = Loading)
        getRefreshLoadStateFlow()
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.filmsRecyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(): Flow<LoadState> {
        return adapter.loadStateFlow.map { it.refresh }
    }

    override fun responseEmpty() {
        showErrorScreen(getString(R.string.empty_server_response_on_success))
    }


    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            model.refresh()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        val manager = requireActivity().getSystemService(Context.SEARCH_SERVICE)
                as SearchManager
        val searchItem = menu.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchItem.apply {

            searchView.also {

                it.setSearchableInfo(manager.getSearchableInfo(activity?.componentName))
                it.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                    override fun onQueryTextSubmit(query: String?): Boolean {

                        if (isNetworkAvailable) {

                            query?.let { searchString ->
                                model.setSearchBy(searchString.toString())
                            }
                            it.clearFocus()
                            it.setQuery("", false)
                            collapseActionView()
                            Toast.makeText(
                                requireContext(),
                                resources.getString(com.cinemaworld.R.string.looking_for) + " " + query
                                    ?: "",
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                               showNoInternetConnectionDialog()
                        }
                        return true
                    }

                    @RequiresApi(Build.VERSION_CODES.N)
                    override fun onQueryTextChange(newText: String?): Boolean {
                        return false
                    }
                })
            }
        }

    }


    companion object {
        fun newInstance() = MainFragment()

    }
}




