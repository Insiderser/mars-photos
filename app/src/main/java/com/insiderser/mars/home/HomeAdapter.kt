package com.insiderser.mars.home

import android.os.Build
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.insiderser.mars.databinding.ItemImageBinding
import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.utils.layoutInflater
import com.squareup.picasso.Picasso

class HomeAdapter(
    private val clickCallback: OnMarsImageClickCallback
) : PagedListAdapter<MarsImage, HomeViewHolder>(MarsImageDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = parent.layoutInflater
        val binding = ItemImageBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding, clickCallback)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class HomeViewHolder(
    private val binding: ItemImageBinding,
    private val clickCallback: OnMarsImageClickCallback
) : RecyclerView.ViewHolder(binding.root) {

    private var currentItem: MarsImage? = null

    init {
        binding.root.setOnClickListener {
            val item = currentItem ?: return@setOnClickListener
            clickCallback.onClick(item, binding.image)
        }
    }

    fun bind(item: MarsImage?) {
        currentItem = item
        binding.image.load(item?.url)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.image.transitionName = "image_${item?.url}"
        }
    }
}

private fun ImageView.load(url: String?) {
    Picasso.get()
        .load(url)
        .placeholder(android.R.drawable.progress_horizontal)
        .into(this)
}

fun interface OnMarsImageClickCallback {
    fun onClick(item: MarsImage, imageView: ImageView)
}

object MarsImageDiffCallback : DiffUtil.ItemCallback<MarsImage>() {

    override fun areItemsTheSame(oldItem: MarsImage, newItem: MarsImage): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MarsImage, newItem: MarsImage): Boolean =
        oldItem == newItem
}
