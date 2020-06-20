package com.insiderser.mars.imageviewer

import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.navigation.navArgs
import com.insiderser.mars.databinding.ActivityImageViewerBinding
import com.squareup.picasso.Picasso

class ImageViewerActivity : LeanBackActivity() {

    private lateinit var binding: ActivityImageViewerBinding

    private val args: ImageViewerActivityArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url: String = args.imageUrl
        binding.image.load(url)
    }
}

private fun ImageView.load(url: String?) {
    val wasViewMeasured = width != 0 || height != 0
    if (!wasViewMeasured) {
        doOnPreDraw {
            load(url)
        }
        return
    }

    Picasso.get()
        .load(url)
        .resize(width, height)
        .centerInside()
        .into(this)
}
