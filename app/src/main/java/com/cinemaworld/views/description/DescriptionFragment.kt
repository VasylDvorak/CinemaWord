package com.cinemaworld.views.description

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cinemaworld.R
import com.cinemaworld.databinding.FragmentDescriptionBinding
import com.cinemaworld.di.ConnectKoinModules.descriptionScreenScope
import com.cinemaworld.model.data_description_request.Cast
import com.cinemaworld.model.data_description_request.DescriptionAppState
import com.cinemaworld.model.data_word_request.Result
import com.cinemaworld.model.repository.OnLineRepository
import com.cinemaworld.utils.delegates.viewById
import com.cinemaworld.utils.ui.loadFilmPhoto
import com.cinemaworld.views.base_for_cinema.BaseFragmentSettingsMenu
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import kotlin.math.roundToInt


const val CURRENT_RESULT = "current_result"

class DescriptionFragment :
    BaseFragmentSettingsMenu<FragmentDescriptionBinding>(FragmentDescriptionBinding::inflate),
    IOnBackPressed {

    private var snack: Snackbar? = null
    private val checkConnection: OnLineRepository by inject()
    private val descriptionFragmentRecyclerview by
    viewById<RecyclerView>(R.id.description_recyclerview)

    lateinit var model: DescriptionViewModel
    private val observer = Observer<DescriptionAppState> { renderData(it) }

    private val adapter: DiscriptionFragmentAdapter
            by lazy { DiscriptionFragmentAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showError()
        initViewModel()
        initViews()
        setData()
    }

    private fun initViewModel() {
        if (descriptionFragmentRecyclerview.adapter != null) {
            throw IllegalStateException(getString(R.string.exception_error))
        }
        val viewModel: DescriptionViewModel by lazy { descriptionScreenScope.get() }
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, observer)
    }

    private fun setData() {
        val currentResult =
            (arguments?.getParcelable(CURRENT_RESULT) as Result?) ?: Result()
        currentResult.id?.let { model.getDataDescription(it, true) }

        binding.apply {
            currentResult.original_title?.let { title.text = it }
            currentResult.poster_path?.let {
                loadFilmPhoto(
                    smallPoster, it,
                    R.drawable.baseline_wallpaper_24
                )
            }

            currentResult.backdrop_path?.let {
                loadFilmPhoto(
                    bigPoster, it,
                    R.drawable.baseline_wallpaper_24
                )
            }

            currentResult.release_date?.let {
                release.text = getString(R.string.relise) + ": " + it
            }

            currentResult.vote_average?.let {
                indicatorRating.progress = (it * 10).roundToInt()
                textRating.text = (it * 10).roundToInt().toString() + "%"
            }
        }
    }

    fun renderData(descriptionAppState: DescriptionAppState) {
        model.setQuery(descriptionAppState)
        when (descriptionAppState) {
            is DescriptionAppState.Success -> {
                binding.progressBarRoundDescription.visibility = View.GONE
                val data = descriptionAppState.data
                if (data == null) {
                    Toast.makeText(context, getString(R.string.example_absent), Toast.LENGTH_LONG)
                } else {
                    data.genres?.let { genres ->
                        var genresString = getString(R.string.genre) + ": "
                        for (genre in genres) {
                            genre?.let { genresString = genresString + it.name + ", " }
                        }
                        binding.genre.text = genresString
                    }
                    binding.duration.text =
                        getString(R.string.duration) + ": " + data.runtime.toString() + " min"
                    data.overview?.let { binding.overview.text = it }
                    updateAdapter(data.credits?.cast)
                }
            }

            is DescriptionAppState.Loading -> {
                Toast.makeText(context, getString(R.string.search_film), Toast.LENGTH_LONG)
            }

            is DescriptionAppState.Error -> {
                Toast.makeText(context, getString(R.string.example_absent), Toast.LENGTH_LONG)
            }
        }
    }


    private fun updateAdapter(cast: List<Cast?>?) {
        adapter?.setData(cast)
    }

    private fun initViews() {
        LinearLayoutManager.HORIZONTAL
        descriptionFragmentRecyclerview.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        descriptionFragmentRecyclerview.adapter = adapter
    }

    private fun showError() {
        checkConnection.observe(
            viewLifecycleOwner
        ) {
            isNetworkAvailable = it
            if (isNetworkAvailable) {
                snack?.dismiss()
                snack = null
                setData()
            } else {
                binding.progressBarRoundDescription.visibility = View.GONE
                snack = Snackbar.make(
                    requireView(),
                    R.string.dialog_message_device_is_offline,
                    Snackbar.LENGTH_INDEFINITE
                )
                snack?.show()
            }
        }
        checkConnection.currentStatus()
    }

    companion object {
        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onBackPressed(): Boolean = false
}

interface IOnBackPressed {
    fun onBackPressed(): Boolean
}
