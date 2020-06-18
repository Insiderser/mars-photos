package com.insiderser.mars.utils

import android.widget.ImageView
import com.insiderser.mars.model.MarsImage
import com.squareup.picasso.Picasso

fun ImageView.load(item: MarsImage?) {
    Picasso.get()
        .load(item?.url)
        .placeholder(android.R.drawable.progress_horizontal)
        .into(this)
}
