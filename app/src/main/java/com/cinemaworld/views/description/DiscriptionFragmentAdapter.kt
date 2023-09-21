package com.cinemaworld.views.description

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cinemaworld.R
import com.cinemaworld.databinding.DescriptionItemBinding
import com.cinemaworld.model.data_description_request.Cast
import com.cinemaworld.utils.ui.loadFilmPhoto


class DiscriptionFragmentAdapter :
    RecyclerView.Adapter<DiscriptionFragmentAdapter.RecyclerItemViewHolder>() {

    private var data: List<Cast> = listOf()
    fun setData(actors: List<Cast?>?) {
        this.data = (actors ?: listOf()) as List<Cast>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val binding =
            DescriptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(val binding: DescriptionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(actor: Cast) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.apply {
                    binding.apply {
                        actor.profilePath?.let {
                            loadFilmPhoto(photo, it, R.drawable.ic_user_avatar)
                        }
                        actor.name?.let { actorName.text = it }
                        actor.character?.let { role.text = it }
                    }
                }
            }
        }
    }
}
