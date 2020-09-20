package com.example.mertkesgin.footballapp.utils

import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageHelper : IImageProces {

    private val mPicassoInstance = Picasso.get()

    override fun loadUrl(url: String, imageView: ImageView) {
        mPicassoInstance.load(url).into(imageView)
    }
}