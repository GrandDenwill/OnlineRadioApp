package com.example.onlineradioapp.adapter

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.onlineradioapp.R
import com.example.onlineradioapp.databinding.RadioRowBinding
import com.example.onlineradioapp.repo.RadioModel

class RadioViewHolder(
     private val binding: RadioRowBinding,
    private val onRowClick:(RadioModel)->Unit,
    private val context: Context
):
    RecyclerView.ViewHolder(binding.root)
{
    fun bindTo(model: RadioModel){
        binding.apply {
            radioName.text = model.radioName
            radioDescription.text = model.radioDescription
            root.setOnClickListener {onRowClick(model) }
            if(model.isFavorite){
                favorited.background =
                    context.let { ContextCompat.getDrawable(it, R.drawable.baseline_star_24) }
            }
        }
    }
}