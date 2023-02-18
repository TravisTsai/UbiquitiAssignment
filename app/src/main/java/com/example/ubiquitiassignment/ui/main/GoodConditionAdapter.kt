package com.example.ubiquitiassignment.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ubiquitiassignment.databinding.ListItemGoodConditionCardBinding
import com.example.ubiquitiassignment.entity.AirPollutionVO

class GoodConditionAdapter : ListAdapter<AirPollutionVO, RecyclerView.ViewHolder>(DiffCallback) {

    inner class ItemViewHolder(
        private val binding: ListItemGoodConditionCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: AirPollutionVO) = binding.apply {
            siteId.text = item.siteId
            siteName.text = item.siteName
            pm25.text = item.pm25
            country.text = item.county
            status.text = item.status.rawValue
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListItemGoodConditionCardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.onBind(getItem(position))
            else -> throw IllegalArgumentException("onBindViewHolder: wrong holder type $holder")
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<AirPollutionVO>() {
        override fun areItemsTheSame(oldItem: AirPollutionVO, newItem: AirPollutionVO): Boolean =
            oldItem.siteId == newItem.siteId

        override fun areContentsTheSame(oldItem: AirPollutionVO, newItem: AirPollutionVO): Boolean =
            oldItem == newItem
    }

}
