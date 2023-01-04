package com.example.amazingtalkerhomework.ui.schedule

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.amazingtalkerhomework.databinding.ItemTimeBinding
import com.example.amazingtalkerhomework.misc.utils.DateUtil
import com.example.amazingtalkerhomework.model.data.PriorState

class ScheduleRecyclerViewAdapter :
    ListAdapter<PriorState, ScheduleRecyclerViewAdapter.ViewHolder>(diffCallback) {
    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<PriorState>() {
            override fun areItemsTheSame(
                oldItem: PriorState,
                newItem: PriorState
            ): Boolean {
                return oldItem.start == newItem.start
            }

            override fun areContentsTheSame(
                oldItem: PriorState,
                newItem: PriorState
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTimeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = currentList[position]
        holder.onBind(item)

    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    fun setData(data: List<PriorState>) {
        submitList(data)
    }

    class ViewHolder(
        private val binding: ItemTimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: PriorState) {
            binding.root.isEnabled = item.available
            binding.tvTime.text = DateUtil.getHourAndMinutes(item.start)
        }
    }
}