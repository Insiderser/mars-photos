package com.insiderser.mars.utils

import android.widget.ImageView
import com.insiderser.mars.model.MarsImage
import com.squareup.picasso.Picasso

fun ImageView.load(item: MarsImage) {
    Picasso.get()
        .load(item.url)
        .into(this)
}
