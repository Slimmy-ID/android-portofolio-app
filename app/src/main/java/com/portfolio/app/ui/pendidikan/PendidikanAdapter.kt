package com.portfolio.app.ui.pendidikan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.app.data.Pendidikan
import com.portfolio.app.databinding.ItemPendidikanBinding

class PendidikanAdapter : ListAdapter<Pendidikan, PendidikanAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPendidikanBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemPendidikanBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: Pendidikan) {
            binding.tvInstitusi.text = item.institusi
            binding.tvJurusan.text = item.jurusan
            binding.tvTahun.text = "${item.tahunMulai} - ${item.tahunSelesai}"
            binding.tvDeskripsi.text = item.deskripsi
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Pendidikan>() {
        override fun areItemsTheSame(oldItem: Pendidikan, newItem: Pendidikan): Boolean {
            return oldItem.institusi == newItem.institusi
        }

        override fun areContentsTheSame(oldItem: Pendidikan, newItem: Pendidikan): Boolean {
            return oldItem == newItem
        }
    }
}
