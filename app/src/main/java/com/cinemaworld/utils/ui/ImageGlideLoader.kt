package com.cinemaworld.utils.ui

import android.widget.ImageView
import com.bumptech.glide.Glide

fun loadFilmPhoto(imageView: ImageView, url: String, drawing: Int){
    val context = imageView.context
         Glide.with(context)
            .load(url)
            .placeholder(drawing)
            .error(drawing)
            .into(imageView)
}