package com.cinemaworld.views.description

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.cinemaworld.R
import com.cinemaworld.databinding.FragmentDescriptionBinding
import com.cinemaworld.di.ConnectKoinModules.descriptionScreenScope
import com.cinemaworld.model.data_description_request.Cast
import com.cinemaworld.model.data_description_request.DescriptionAppState
import com.cinemaworld.model.data_word_request.Result
import com.cinemaworld.model.repository.OnLineRepository
import com.cinemaworld.utils.delegates.viewById
import com.cinemaworld.views.base_for_dictionary.BaseFragmentSettingsMenu
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

private const val delayForButtom = 400L

private const val delayImage = 1000
const val CURRENT_RESULT = "current_result"

class DescriptionFragment :
    BaseFragmentSettingsMenu<FragmentDescriptionBinding>(FragmentDescriptionBinding::inflate) {

    private var snack: Snackbar? = null
    private val checkConnection: OnLineRepository by inject()
    private val descriptionFragmentRecyclerview by
    viewById<RecyclerView>(R.id.description_recyclerview)

    lateinit var model: DescriptionViewModel
    private val observer = Observer<DescriptionAppState> { renderData(it) }

    private val adapter: DiscriptionFragmentAdapter
            by lazy { DiscriptionFragmentAdapter(::onPlayClick) }


    private fun onPlayClick(url: String) {
        model.playContentUrl(url)
    }


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
            descriptionHeader.text = currentResult.text
            if (currentResult.meanings?.size != 0) {
                descriptionPartOfSpeech.text =
                    currentResult.meanings?.get(0)?.partOfSpeechCode
                translationTextview.text =
                    currentResult.meanings?.get(0)?.translation?.translation
                transcription.text =
                    "[" + currentResult.meanings?.get(0)?.transcription + "]"

                playArticulation.setOnClickListener {
                    it?.apply {
                        isEnabled = false
                        postDelayed({ isEnabled = true }, delayForButtom)
                    }
                    currentResult.meanings?.get(0)?.soundUrl?.let { soundUrl ->
                    }
                }
                val imageLink = currentResult.meanings?.get(0)?.imageUrl
                if (imageLink.isNullOrBlank()) {
                    //    stopRefreshAnimationIfNeeded()
                } else {
                    useGlideToLoadPhoto(descriptionImageview, imageLink)
                }
            }
        }
    }

    fun renderData(descriptionAppState: DescriptionAppState) {
        model.setQuery(descriptionAppState)
        when (descriptionAppState) {
            is DescriptionAppState.Success -> {
                binding.progressBarRoundDescription.visibility = View.GONE
                val data = descriptionAppState.data
                if (data==null) {
                    Toast.makeText(context, getString(R.string.example_absent), Toast.LENGTH_LONG)
                } else {
                    updateAdapter(data.credits?.cast)
                }
            }

            is DescriptionAppState.Loading -> {
                binding.progressBarRoundDescription.visibility = View.VISIBLE
            }

            is DescriptionAppState.Error -> {
                binding.progressBarRoundDescription.visibility = View.GONE
                Toast.makeText(context, getString(R.string.example_absent), Toast.LENGTH_LONG)
            }
        }
    }


    private fun updateAdapter(cast: List<Cast?>?) {
        adapter?.setData(cast)
    }

    private fun initViews() {
        descriptionFragmentRecyclerview.layoutManager =
            LinearLayoutManager(context)
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


    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
        Glide.with(this)
            .load("https:$imageLink")
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    imageView.setImageResource(R.drawable.baseline_person_off_24)
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.baseline_person_off_24)
                    .centerCrop()
            )
            .transition(DrawableTransitionOptions.withCrossFade(delayImage))
            .transform(CircleCrop())
            .into(imageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    companion object {
        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }

    }
}
