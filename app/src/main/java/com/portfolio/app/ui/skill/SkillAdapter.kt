package com.portfolio.app.ui.skill

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.portfolio.app.data.Skill
import com.portfolio.app.databinding.ItemSkillBinding

class SkillAdapter : ListAdapter<Skill, SkillAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSkillBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val binding: ItemSkillBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(item: Skill) {
            binding.tvSkillName.text = item.nama
            binding.tvKategori.text = item.kategori
            binding.tvLevel.text = "${item.level}%"
            binding.progressBar.progress = item.level
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Skill>() {
        override fun areItemsTheSame(oldItem: Skill, newItem: Skill): Boolean {
            return oldItem.nama == newItem.nama
        }

        override fun areContentsTheSame(oldItem: Skill, newItem: Skill): Boolean {
            return oldItem == newItem
        }
    }
}
