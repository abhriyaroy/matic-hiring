package com.example.maticnetwork.view

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {
  fun loadImage(imageView: ImageView, @DrawableRes imageRes: Int)
}

class ImageLoaderImpl(private val context: Context) : ImageLoader {
  override fun loadImage(imageView: ImageView, @DrawableRes imageRes: Int) {
    imageView.setImageDrawable(context.resources.getDrawable(imageRes))
  }

}