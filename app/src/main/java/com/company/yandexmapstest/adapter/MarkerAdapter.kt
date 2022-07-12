package com.company.yandexmapstest.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.company.yandexmapstest.databinding.CardMarkerBinding


interface OnInteractionListener {
    fun onClick(marker: String) {}
}

class MarkerAdapter(private val listener: OnInteractionListener) : ListAdapter<String, MarkerViewHolder>(MarkerDiffCallback()) {
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
    fun bind(marker: String) {
        binding.tvMarker.text = marker
        binding.root.setOnClickListener {
            listener.onClick(marker)
        }
    }
}

class MarkerDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}