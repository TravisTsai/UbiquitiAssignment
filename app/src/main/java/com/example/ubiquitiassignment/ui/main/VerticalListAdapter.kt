package com.example.ubiquitiassignment.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ubiquitiassignment.R
import com.example.ubiquitiassignment.databinding.ListItemBadConditionCardBinding
import com.example.ubiquitiassignment.entity.AirPollutionVO

class VerticalListAdapter : ListAdapter<AirPollutionVO, RecyclerView.ViewHolder>(DiffCallback) {

    inner class ItemViewHolder(
        private val binding: ListItemBadConditionCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: AirPollutionVO) = binding.apply {
            siteId.text = item.siteId
            siteName.text = item.siteName
            pm25.text = item.pm25
            country.text = item.county

            val context = binding.root.context
            status.text = if (item.isGoodStatus()) {
                context.getString(R.string.main_page_good_condition_text)
            } else {
                item.status.rawValue
            }

            moreButton.apply {
                isVisible = item.isGoodStatus().not()
                setOnClickListener {
                    Toast.makeText(context, item.status.rawValue, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(
            ListItemBadConditionCardBinding.inflate(
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
