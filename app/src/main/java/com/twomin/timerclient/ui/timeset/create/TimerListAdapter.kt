package com.twomin.timerclient.ui.timeset.create

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.twomin.timerclient.R
import com.twomin.timerclient.Timer
import com.twomin.timerclient.databinding.ItemCreateTimeSetBinding
import kotlinx.android.synthetic.main.item_create_time_set.view.*

class TimerListAdapter : RecyclerView.Adapter<TimerListAdapter.TimerViewHolder>() {
    val item = mutableListOf<Timer>().apply {
        for (i in 1..15)
            add(Timer(i, ""))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        return TimerViewHolder(
            ItemCreateTimeSetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(item[position])
    }

    override fun getItemCount(): Int {
        return item.size
    }

    class TimerViewHolder(private val binding: ItemCreateTimeSetBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(timer: Timer) {
            val resources = itemView.resources
            val originContentWidth = resources.getDimensionPixelOffset(R.dimen.createTimerItemWidth)
            val originContentHeight = resources.getDimensionPixelOffset(R.dimen.createTimerItemHeight)
            val layoutParam = binding.content.layoutParams as ConstraintLayout.LayoutParams
            layoutParam.dimensionRatio = "h, $originContentWidth:$originContentHeight"
            binding.timer = timer
        }
    }
}