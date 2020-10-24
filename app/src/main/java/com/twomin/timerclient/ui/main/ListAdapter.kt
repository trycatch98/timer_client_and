package com.twomin.timerclient.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.twomin.timerclient.BR
import com.twomin.timerclient.TimeSet
import com.twomin.timerclient.TimeSetType
import com.twomin.timerclient.databinding.ItemHourTimeSetBinding
import com.twomin.timerclient.databinding.ItemMinuteTimeSetBinding
import com.twomin.timerclient.databinding.ItemSecondTimeSetBinding
import kotlinx.android.synthetic.main.item_hour_time_set.view.*

abstract class BaseAdapter : ListAdapter<TimeSet, BaseAdapter.BaseViewHolder>(DiffCallback()) {
    companion object {
        private const val VIEW_TYPE_HOUR = 0
        private const val VIEW_TYPE_MINUTE = 1
        private const val VIEW_TYPE_SECOND = 2
    }

    class DiffCallback : DiffUtil.ItemCallback<TimeSet>() {
        override fun areItemsTheSame(oldItem: TimeSet, newItem: TimeSet): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: TimeSet, newItem: TimeSet): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface OnItemClickListener {
        fun onClickItem(item: TimeSet)
    }

    interface OnTaskClickListener {
        fun onHideItem(item: TimeSet)
        fun onDeleteItem(item: TimeSet)
    }

    private var itemClickListener: OnItemClickListener? = null
    protected var taskClickListener: OnTaskClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_HOUR -> {
                ItemHourTimeSetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            VIEW_TYPE_MINUTE -> {
                ItemMinuteTimeSetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            VIEW_TYPE_SECOND -> {
                ItemSecondTimeSetBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            else -> throw Exception("Unknown view type")
        }


        return createViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).type) {
            is TimeSetType.HourTimeSet -> VIEW_TYPE_HOUR
            is TimeSetType.MinuteTimeSet -> VIEW_TYPE_MINUTE
            is TimeSetType.SecondTimeSet -> VIEW_TYPE_SECOND
        }
    }

    fun setOnItemClickListener(onItemClickListener: (TimeSet) -> Unit) {
        this.itemClickListener = object : OnItemClickListener {
            override fun onClickItem(item: TimeSet) {
                onItemClickListener(item)
            }
        }
    }

    fun setOnTaskClickListener(onTaskClickListener: OnTaskClickListener) {
        this.taskClickListener = object : OnTaskClickListener {
            override fun onHideItem(item: TimeSet) {
                onTaskClickListener.onHideItem(item)
            }

            override fun onDeleteItem(item: TimeSet) {
                onTaskClickListener.onDeleteItem(item)
            }
        }
    }

    abstract fun createViewHolder(binding: ViewDataBinding): BaseViewHolder

    open class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(timeSet: TimeSet) {
            binding.setVariable(BR.timeSet, timeSet)
        }
    }
}

class RecentTimeSetAdapter : BaseAdapter() {

    override fun createViewHolder(binding: ViewDataBinding) = RecentViewHolder(binding)

    inner class RecentViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding) {
        override fun bind(timeSet: TimeSet) {
            super.bind(timeSet)
            binding.setVariable(BR.task, "삭제")
            itemView.task.setOnClickListener {
                taskClickListener?.onDeleteItem(timeSet)
            }
        }
    }
}

class SaveTimeSetAdapter : BaseAdapter() {
    override fun createViewHolder(binding: ViewDataBinding) = RecentViewHolder(binding)

    inner class RecentViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding) {
        override fun bind(timeSet: TimeSet) {
            super.bind(timeSet)
            binding.setVariable(BR.task, "숨김")
            itemView.task.setOnClickListener {
                taskClickListener?.onHideItem(timeSet)
            }
        }
    }
}

class PresetTimeSetAdapter : BaseAdapter() {
    override fun createViewHolder(binding: ViewDataBinding) = RecentViewHolder(binding)

    class RecentViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding)
}