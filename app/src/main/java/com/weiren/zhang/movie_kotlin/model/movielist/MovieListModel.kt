package com.weiren.zhang.movie_kotlin.model.movielist

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String) {
    Glide.with(imageView.context)
        .load(url)
        .transition(withCrossFade())
        .into(imageView)
}

data class MovieListModel(
    val title: String,
    val en: String,
    val release_movie_time: String,
    val thumb: String,
    var id: String
)


