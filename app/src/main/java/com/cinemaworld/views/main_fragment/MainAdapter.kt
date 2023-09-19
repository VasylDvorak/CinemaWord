package com.cinemaworld.views.main_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cinemaworld.R
import com.cinemaworld.databinding.RecyclerviewItemBinding
import com.cinemaworld.model.data_description_request.Cast
import com.cinemaworld.model.data_word_request.Result

class MainAdapter(
    private var onListItemClickListener: (Result) -> Unit
) : RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<Result> = listOf()
    fun setData(data: List<Result?>?) {
        this.data = (data ?: listOf()) as List<Result>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(result: Result) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    binding.apply {
                        headerTextviewRecyclerItem.text = result.text
                        descriptionTextviewRecyclerItem.text =
                            result.meanings?.get(0)?.translation?.translation
                        transcriptionTextviewRecyclerItem.text =
                            "[" + result.meanings?.get(0)?.transcription + "]"

                        setFavorite.apply {
                            if (result.inFavoriteList) {
                                setImageResource(R.drawable.baseline_favorite_24)
                            } else {
                                setImageResource(R.drawable.baseline_favorite_border_24)
                            }
                            setOnClickListener {
                                result.inFavoriteList = !result.inFavoriteList
                                if (result.inFavoriteList) {
                                    setImageResource(R.drawable.baseline_favorite_24)
                                } else {
                                    setImageResource(R.drawable.baseline_favorite_border_24)
                                }
                                putInFavoriteList(result, position, result.inFavoriteList)
                            }
                        }
                        playArticulation.setOnClickListener {
                            it?.apply {
                                isEnabled = false
                                postDelayed({ isEnabled = true }, 400)
                            }
                            result.meanings?.get(0)?.soundUrl?.let { soundUrl ->
                                playArticulationClickListener(soundUrl)
                            }
                        }

                        itemView.setOnClickListener { openInNewWindow(result) }
                    }
                }
            }
        }
    }


    private fun openInNewWindow(result: Result) {
        onListItemClickListener(result)
    }
}
