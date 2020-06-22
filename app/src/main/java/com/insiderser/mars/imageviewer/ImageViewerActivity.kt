package com.insiderser.mars.imageviewer

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.insiderser.mars.R
import com.insiderser.mars.databinding.ActivityImageViewerBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import timber.log.Timber

class ImageViewerActivity : LeanBackActivity() {

    private lateinit var binding: ActivityImageViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImageViewerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportPostponeEnterTransition()

        val url = intent.getStringExtra(EXTRA_URL)
        requireNotNull(url) { "You must pass URL as an intent extra." }
        loadImage(url)
    }

    private fun loadImage(url: String): Unit = with(binding.image) {
        val callback = object : Callback {

            override fun onSuccess() {
                supportStartPostponedEnterTransition()
            }

            override fun onError(e: Exception?) {
                supportStartPostponedEnterTransition()

                Timber.e(e, "Cannot load image $url")

                Snackbar.make(
                    binding.root,
                    getString(R.string.image_loading_error),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        Picasso.get()
            .load(url)
            .noFade()
            .into(this, callback)
    }

    companion object {
        const val EXTRA_URL = "image-url"
    }
}
