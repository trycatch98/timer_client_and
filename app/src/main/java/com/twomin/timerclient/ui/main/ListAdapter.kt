package com.twomin.timerclient.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.twomin.timerclient.BR
import com.twomin.timerclient.Timer
import com.twomin.timerclient.TimerType
import com.twomin.timerclient.databinding.ItemHourTimerBinding
import com.twomin.timerclient.databinding.ItemMinuteTimerBinding
import com.twomin.timerclient.databinding.ItemSecondTimerBinding

abstract class BaseAdapter : ListAdapter<Timer, BaseAdapter.BaseViewHolder>(DiffCallback()) {
    companion object {
        private const val VIEW_TYPE_HOUR = 0
        private const val VIEW_TYPE_MINUTE = 1
        private const val VIEW_TYPE_SECOND = 2
    }

    class DiffCallback : DiffUtil.ItemCallback<Timer>() {
        override fun areItemsTheSame(oldItem: Timer, newItem: Timer): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Timer, newItem: Timer): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface ItemClickListener {
        fun onClickItem(item: String, position: Int)
    }

    private var onItemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = when (viewType) {
            VIEW_TYPE_HOUR -> {
                ItemHourTimerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            VIEW_TYPE_MINUTE -> {
                ItemMinuteTimerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
            VIEW_TYPE_SECOND -> {
                ItemSecondTimerBinding.inflate(
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
            is TimerType.HourTimer -> VIEW_TYPE_HOUR
            is TimerType.MinuteTimer -> VIEW_TYPE_MINUTE
            is TimerType.SecondTimer -> VIEW_TYPE_SECOND
        }
    }

    fun setOnItemClickListener(onItemClickListener: (String, Int) -> Unit) {
        this.onItemClickListener = object : ItemClickListener {
            override fun onClickItem(item: String, position: Int) {
                onItemClickListener(item, position)
            }
        }
    }

    abstract fun createViewHolder(binding: ViewDataBinding): BaseViewHolder

    open class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        open fun bind(timer: Timer) {
            binding.setVariable(BR.timer, timer)
        }
    }
}

class RecentTimerAdapter : BaseAdapter() {
    override fun createViewHolder(binding: ViewDataBinding) = RecentViewHolder(binding)

    class RecentViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding) {
        override fun bind(timer: Timer) {
            super.bind(timer)
            binding.setVariable(BR.task, "삭제")
        }
    }
}

class SaveTimerAdapter : BaseAdapter() {
    override fun createViewHolder(binding: ViewDataBinding) = RecentViewHolder(binding)

    class RecentViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding) {
        override fun bind(timer: Timer) {
            super.bind(timer)
            binding.setVariable(BR.task, "숨김")
        }
    }
}

class PresetTimerAdapter : BaseAdapter() {
    override fun createViewHolder(binding: ViewDataBinding) = RecentViewHolder(binding)

    class RecentViewHolder(private val binding: ViewDataBinding) : BaseViewHolder(binding)
}