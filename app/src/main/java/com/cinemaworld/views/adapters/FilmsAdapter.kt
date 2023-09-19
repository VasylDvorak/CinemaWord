package com.cinemaworld.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cinemaworld.R
import com.cinemaworld.databinding.ItemFilmBinding
import com.cinemaworld.model.data_word_request.Result
import com.google.gson.Gson


/**
 * Adapter for rendering users list in a RecyclerView.
 */
class FilmsAdapter : PagingDataAdapter<Pair<Result?, Result?>, FilmsAdapter.Holder>(
    FilmsDiffCallback()
) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = getItem(position) ?: return
        with (holder.binding) {
            if(user != null){
            userNameTextView.text = user.first?.name
            userCompanyTextView.text = user.first?.original_title
                user.first?.poster_path?.let { loadFilmPhoto(photoImageView, it) }
        }else{
                userNameTextView.text = ""
                userCompanyTextView.text = ""
                loadFilmPhoto(photoImageView, "")
            }
    }}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFilmBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }

    private fun loadFilmPhoto(imageView: ImageView, url: String) {
        val context = imageView.context
        if (url.isNotBlank()) {
            Glide.with(context)
                .load(url)
                .circleCrop()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(R.drawable.ic_user_avatar)
                .into(imageView)
        }
    }

    class Holder(
        val binding: ItemFilmBinding
    ) : RecyclerView.ViewHolder(binding.root)

}

// ---

class FilmsDiffCallback : DiffUtil.ItemCallback<Pair<Result?, Result?>>() {
    private val gson by lazy{Gson()}
    override fun areItemsTheSame(oldItem: Pair<Result?, Result?>, newItem: Pair<Result?, Result?>): Boolean {
        return (oldItem.first?.id == newItem.first?.id)&&(oldItem.second?.id == newItem.second?.id)
    }

    override fun areContentsTheSame(oldItem: Pair<Result?, Result?>, newItem: Pair<Result?, Result?>): Boolean {
        return   gson.toJson(oldItem) == gson.toJson(newItem)
    }
}
