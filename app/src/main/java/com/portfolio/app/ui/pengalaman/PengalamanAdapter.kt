package com.portfolio.app.ui.pengalaman

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.app.data.Pengalaman
import com.portfolio.app.databinding.ItemPengalamanBinding

class PengalamanAdapter : ListAdapter<Pengalaman, PengalamanAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPengalamanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemPengalamanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: Pengalaman) {
            binding.tvPerusahaan.text = item.perusahaan
            binding.tvPosisi.text = item.posisi
            binding.tvTahun.text = "${item.tahunMulai} - ${item.tahunSelesai}"
            binding.tvDeskripsi.text = item.deskripsi
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Pengalaman>() {
        override fun areItemsTheSame(oldItem: Pengalaman, newItem: Pengalaman): Boolean {
            return oldItem.perusahaan == newItem.perusahaan
        }

        override fun areContentsTheSame(oldItem: Pengalaman, newItem: Pengalaman): Boolean {
            return oldItem == newItem
        }
    }
}
