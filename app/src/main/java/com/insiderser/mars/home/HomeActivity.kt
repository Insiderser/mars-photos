package com.insiderser.mars.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.insiderser.mars.R
import com.insiderser.mars.databinding.ActivityHomeBinding
import com.insiderser.mars.imageviewer.ImageViewerActivity
import com.insiderser.mars.model.MarsImage
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = HomeAdapter { item, view ->
        openImageFullscreen(item, view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.root.setEdgeToEdgeSystemUiFlags()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val spanCount = resources.getInteger(R.integer.home_list_span_count)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setItemViewCacheSize(spanCount)

        viewModel.images.observe(this) { adapter.submitList(it) }
    }

    private fun openImageFullscreen(image: MarsImage, sharedView: View) {
        val intent = Intent(this, ImageViewerActivity::class.java)
            .putExtra(ImageViewerActivity.EXTRA_URL, image.url)

        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, sharedView, getString(R.string.image_viewer_image_transition_name)
        )

        startActivity(intent, activityOptions.toBundle())
    }
}
