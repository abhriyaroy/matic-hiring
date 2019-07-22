package com.example.maticnetwork.view

import android.content.Context
import android.os.Build
import android.widget.ImageView
import androidx.annotation.DrawableRes

interface ImageLoader {
  fun loadImage(imageView: ImageView, @DrawableRes imageRes: Int)
}

class ImageLoaderImpl(private val context: Context) : ImageLoader {
  override fun loadImage(imageView: ImageView, @DrawableRes imageRes: Int) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      imageView.setImageDrawable(context.resources.getDrawable(imageRes))
    } else {
      imageView.setImageDrawable(context.getDrawable(imageRes))
    }
  }

}