package com.insiderser.mars.imageviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.insiderser.mars.databinding.FragmentImageViewerBinding
import com.squareup.picasso.Picasso
import dev.chrisbanes.insetter.applySystemWindowInsetsToMargin
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags

class ImageViewerFragment : Fragment() {

    private lateinit var binding: FragmentImageViewerBinding

    private val args: ImageViewerFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentImageViewerBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rootLayout.setEdgeToEdgeSystemUiFlags()
        binding.image.applySystemWindowInsetsToMargin(left = true, top = true, right = true, bottom = true)

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
