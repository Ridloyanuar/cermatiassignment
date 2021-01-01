package org.dlo.myapplication.ui.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso


object CustomBindingAdapter{

    @JvmStatic
    @BindingAdapter("bind:image_url")
    fun loadImage(imageView: ImageView, url: String?) {
        url?.let {
            Picasso.with(imageView.context).load(it).into(imageView)
        }
    }

}