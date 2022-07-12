package com.company.yandexmapstest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.yandexmapstest.R
import com.company.yandexmapstest.databinding.CardMarkerBinding
import com.company.yandexmapstest.entity.MarkerEntity


interface OnInteractionListener {
    fun onClick(marker: MarkerEntity) {}
    fun onRemove(marker: MarkerEntity) {}
}

class MarkerAdapter(private val listener: OnInteractionListener) :
    ListAdapter<MarkerEntity, MarkerViewHolder>(MarkerDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        val binding = CardMarkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkerViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        val marker = getItem(position)
        holder.bind(marker)
    }
}

class MarkerViewHolder(
    private val binding: CardMarkerBinding,
    private val listener: OnInteractionListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(marker: MarkerEntity) {
        with(binding) {
            tvMarker.text = "${marker.latitude}, ${marker.longitude}"
            root.setOnClickListener {
                listener.onClick(marker)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_marker)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                listener.onRemove(marker)
                                true
                            }
                            R.id.edit -> {
//                                onInteractionListener.onEdit(marker)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

class MarkerDiffCallback : DiffUtil.ItemCallback<MarkerEntity>() {
    override fun areItemsTheSame(oldItem: MarkerEntity, newItem: MarkerEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MarkerEntity, newItem: MarkerEntity): Boolean {
        return oldItem == newItem
    }
}