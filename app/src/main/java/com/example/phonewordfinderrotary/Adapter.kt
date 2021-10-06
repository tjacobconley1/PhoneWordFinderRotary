package com.example.phonewordfinderrotary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.phonewordfinderrotary.databinding.WordItemLayoutBinding

class Adapter: RecyclerView.Adapter<Adapter.WordViewHolder>() {
    inner class WordViewHolder(val binding: WordItemLayoutBinding): RecyclerView.ViewHolder(binding.root)

    var words = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = WordItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context)
        )
        return WordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.binding.wordTextview.text = words[position]
    }

    override fun getItemCount(): Int = words.size
}