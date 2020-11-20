package com.twomin.timerclient.ui.timeset.create

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TimerPagerAdapter(
    fragment: Fragment,
    private val items: MutableList<TimerPageFragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment = items[position]

    override fun getItemId(position: Int): Long {
        return items[position].hashCode().toLong()
    }

    fun removeItem(item: TimerPageFragment) {
        val position = items.indexOf(item)
        items.removeAt(position)
        notifyItemRemoved(position)
    }
}