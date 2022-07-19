package com.company.yandexmapstest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.yandexmapstest.R
import com.company.yandexmapstest.databinding.CardMarkerBinding
import com.company.yandexmapstest.dto.Marker


interface OnInteractionListener {
    fun onClick(marker: Marker) {}
    fun onRemove(marker: Marker) {}
//    fun onEdit(marker: Marker) {}
}

class MarkerAdapter(private val listener: OnInteractionListener) :
    ListAdapter<Marker, MarkerViewHolder>(MarkerDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerViewHolder {
        val binding = CardMarkerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarkerViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MarkerViewHolder, position: Int) {
        val marker = getItem(position)
        holder.bind(marker)
    }
}


class MarkerViewHolder (
    private val binding: CardMarkerBinding,
    private val listener: OnInteractionListener
) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(marker: Marker) {
        with(binding) {
            tvMarker.text = marker.description
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

class MarkerDiffCallback : DiffUtil.ItemCallback<Marker>() {
    override fun areItemsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Marker, newItem: Marker): Boolean {
        return oldItem == newItem
    }
}