package com.insiderser.mars.home

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.insiderser.mars.databinding.ListItemImageBinding
import com.insiderser.mars.model.MarsImage
import com.insiderser.mars.utils.layoutInflater
import com.insiderser.mars.utils.load

class HomeAdapter(
    private val clickCallback: OnMarsImageClickCallback
) : PagedListAdapter<MarsImage, HomeViewHolder>(MarsImageDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val inflater = parent.layoutInflater
        val binding = ListItemImageBinding.inflate(inflater, parent, false)
        return HomeViewHolder(binding, clickCallback)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class HomeViewHolder(
    private val binding: ListItemImageBinding,
    private val clickCallback: OnMarsImageClickCallback
) : RecyclerView.ViewHolder(binding.root) {

    private var currentItem: MarsImage? = null

    init {
        binding.root.setOnClickListener { root ->
            val item = currentItem ?: return@setOnClickListener
            clickCallback.onClick(item, root)
        }
    }

    fun bind(item: MarsImage?) {
        currentItem = item
        binding.image.load(item)
    }
}

fun interface OnMarsImageClickCallback {
    fun onClick(item: MarsImage, itemView: View)
}

object MarsImageDiffCallback : DiffUtil.ItemCallback<MarsImage>() {

    override fun areItemsTheSame(oldItem: MarsImage, newItem: MarsImage): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: MarsImage, newItem: MarsImage): Boolean =
        oldItem == newItem
}
