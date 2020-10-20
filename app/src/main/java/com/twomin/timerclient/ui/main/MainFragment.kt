package com.twomin.timerclient.ui.main

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.twomin.timerclient.R
import com.twomin.timerclient.Timer
import com.twomin.timerclient.TimerType
import com.twomin.timerclient.databinding.FragmentMainBinding
import com.twomin.timerclient.ui.common.BaseFragment


class MainFragment : BaseFragment<MainViewModel, FragmentMainBinding>(
    R.layout.fragment_main
) {
    override val viewModel by viewModels<MainViewModel>()

    override fun initView() {
        super.initView()
        initList(binding.recentList, RecentTimerAdapter())
        initList(binding.saveList, SaveTimerAdapter())
        initList(binding.presetList, PresetTimerAdapter())

        setSwipe(binding.recentList)
        setSwipe(binding.saveList)
    }

    private fun initList(recyclerView: RecyclerView, listAdapter: BaseAdapter) {
        recyclerView.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(TimerDecoration())
        }

        listAdapter.submitList(
            listOf(
                Timer(
                    0,
                    "Hour",
                    TimerType.HourTimer("99", "99", "99")
                ),
                Timer(
                    1,
                    "Minute",
                    TimerType.MinuteTimer("99", "99")
                ),
                Timer(
                    2,
                    "Second",
                    TimerType.SecondTimer("99")
                )
            )
        )
    }

    private fun setSwipe(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(
            SwipeHelperCallback().apply {
                setClamp(resources.getDimension(R.dimen.swipeClamp))
            }
        )
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}