package com.portfolio.app.ui.portofolio

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.app.data.Portofolio
import com.portfolio.app.databinding.ItemPortofolioBinding

class PortofolioAdapter : ListAdapter<Portofolio, PortofolioAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPortofolioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemPortofolioBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: Portofolio) {
            binding.tvJudul.text = item.judul
            binding.tvDeskripsi.text = item.deskripsi
            binding.tvTeknologi.text = item.teknologi
            
            binding.root.setOnClickListener {
                if (item.link.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.link))
                    it.context.startActivity(intent)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Portofolio>() {
        override fun areItemsTheSame(oldItem: Portofolio, newItem: Portofolio): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Portofolio, newItem: Portofolio): Boolean {
            return oldItem == newItem
        }
    }
}
