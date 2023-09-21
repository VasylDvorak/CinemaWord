package com.cinemaworld.views.main_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinemaworld.R
import com.cinemaworld.databinding.FilmCardItemBinding
import com.cinemaworld.databinding.ItemFilmBinding
import com.cinemaworld.model.data_word_request.Result
import com.cinemaworld.utils.ui.loadFilmPhoto
import com.google.gson.Gson
import kotlin.math.roundToInt


/**
 * Adapter for rendering users list in a RecyclerView.
 */
class FilmsAdapter(
    private var onListItemClickListener: (Result) -> Unit
) : PagingDataAdapter<Pair<Result?, Result?>, FilmsAdapter.Holder>(
    FilmsDiffCallback()
) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val film = getItem(position) ?: return
        with(holder.binding) {
            film.first?.let { result ->
                firstCard.apply {
                    fillWithViewCard(this, result)
                }
            }

            film.second?.let { result ->
                secondCard.apply {
                    fillWithViewCard(this, result)
                }
            }
        }
    }

    private fun fillWithViewCard(filmCardItemBinding: FilmCardItemBinding, result: Result) {
        if (!((result.release_date.isNullOrEmpty()) && (result.original_title.isNullOrEmpty()))) {
            filmCardItemBinding.apply {
                card.visibility = View.VISIBLE
                result.poster_path?.let {
                    loadFilmPhoto(
                        posterInList, it,
                        R.drawable.baseline_wallpaper_24
                    )
                }
                result.vote_average?.let {
                    indicatorRating.progress = (it * 10).roundToInt()
                    textRating.text = (it * 10).roundToInt().toString() + "%"
                }
                titleFilmInList.text = result.original_title
                releaseFilmInList.text = result.release_date
                card.setOnClickListener { openInNewWindow(result) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }


    private fun openInNewWindow(result: Result) {
        onListItemClickListener(result)
    }

    class Holder(
        val binding: ItemFilmBinding
    ) : RecyclerView.ViewHolder(binding.root)

}


class FilmsDiffCallback : DiffUtil.ItemCallback<Pair<Result?, Result?>>() {
    private val gson by lazy { Gson() }
    override fun areItemsTheSame(
        oldItem: Pair<Result?, Result?>,
        newItem: Pair<Result?, Result?>
    ): Boolean {
        return (oldItem.first?.id == newItem.first?.id) && (oldItem.second?.id == newItem.second?.id)
    }

    override fun areContentsTheSame(
        oldItem: Pair<Result?, Result?>,
        newItem: Pair<Result?, Result?>
    ): Boolean {
        return gson.toJson(oldItem) == gson.toJson(newItem)
    }
}
