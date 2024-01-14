package com.example.marvelapp.framework.imageloader

import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import javax.inject.Inject

interface ImageLoader {
    fun load(imageView: ImageView, imageUrl: String, @DrawableRes fallback: Int)
}

class GlideImageLoader @Inject constructor(): ImageLoader {
    override fun load(imageView: ImageView, imageUrl: String, @DrawableRes fallback: Int) {
        Glide.with(imageView.rootView)
            .load(imageUrl)
            .fallback(fallback)
            .into(imageView)
    }
}