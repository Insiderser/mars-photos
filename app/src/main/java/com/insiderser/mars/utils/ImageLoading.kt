package com.insiderser.mars.utils

import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import com.insiderser.mars.model.MarsImage
import com.squareup.picasso.Picasso

fun ImageView.load(item: MarsImage?) {
    val wasViewMeasured = width != 0 || height != 0
    if (!wasViewMeasured) {
        doOnPreDraw {
            load(item)
        }
        return
    }

    Picasso.get()
        .load(item?.url)
        .placeholder(android.R.drawable.progress_horizontal)
        .resize(width, height)
        .centerCrop()
        .into(this)
}
