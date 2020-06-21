package com.insiderser.mars.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.insiderser.mars.R
import com.insiderser.mars.databinding.FragmentHomeBinding
import com.insiderser.mars.model.MarsImage
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.setEdgeToEdgeSystemUiFlags

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private val adapter = HomeAdapter { item, view ->
        openImageFullscreen(item, view)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        FragmentHomeBinding.inflate(inflater, container, false).also {
            binding = it
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.setEdgeToEdgeSystemUiFlags()

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val spanCount = resources.getInteger(R.integer.home_list_span_count)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.setItemViewCacheSize(spanCount)

        viewModel.images.observe(viewLifecycleOwner) { adapter.submitList(it) }
    }

    @Suppress("UNREACHABLE_CODE")
    private fun openImageFullscreen(image: MarsImage, sharedView: View) {
        val navController = findNavController()
        val direction: NavDirections = HomeFragmentDirections.viewImageFullscreen(image.url)

        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
            requireActivity(), sharedView, getString(R.string.image_viewer_image_transition_name)
        )

        val extra = ActivityNavigator.Extras.Builder()
            .setActivityOptions(activityOptions)
            .build()

        navController.navigate(direction, extra)
    }
}
