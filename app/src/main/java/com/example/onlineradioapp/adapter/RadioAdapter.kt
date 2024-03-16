package com.example.onlineradioapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.onlineradioapp.databinding.RadioRowBinding
import com.example.onlineradioapp.repo.RadioModel

class RadioAdapter(private val inflater: LayoutInflater, private val onRowClick: (RadioModel) -> Unit): ListAdapter<RadioModel, RadioViewHolder>(
    StateDiffer
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioViewHolder {
        return RadioViewHolder(RadioRowBinding.inflate(inflater,parent,false), onRowClick = onRowClick,inflater.context)
    }
    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        var item =getItem(position)
        holder.bindTo(getItem(position))
    }
    private object StateDiffer: DiffUtil.ItemCallback<RadioModel>(){
        override fun areItemsTheSame(oldItem: RadioModel, newItem: RadioModel): Boolean {
            return oldItem.radioId == newItem.radioId
        }
        override fun areContentsTheSame(oldItem: RadioModel, newItem: RadioModel): Boolean {
            return areItemsTheSame(oldItem,newItem)
        }
    }
}