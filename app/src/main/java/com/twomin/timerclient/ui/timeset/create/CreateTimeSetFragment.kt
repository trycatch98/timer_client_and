package com.twomin.timerclient.ui.timeset.create

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import com.twomin.timerclient.R
import com.twomin.timerclient.databinding.FragmentCreateTimeSetBinding
import com.twomin.timerclient.ui.common.BaseFragment
import java.util.*


class CreateTimeSetFragment : BaseFragment<CreateTimeSetViewModel, FragmentCreateTimeSetBinding>(
    R.layout.fragment_create_time_set
) {
    override val viewModel by viewModels<CreateTimeSetViewModel>()

    private lateinit var timerListAdapter: TimerPagerAdapter

    private lateinit var timeTickReceiver: BroadcastReceiver

    interface OnAttachTimeListener {
        fun onAttachTime(time: LiveData<Date>)
    }

    interface OnTimerDeleteListener {
        fun onTimerDelete(timerPageFragment: TimerPageFragment, time: LiveData<Date>)
    }

    override fun initView() {
        super.initView()
        initTimerViewPager()
    }

    override fun initObserve() {
        super.initObserve()

        timeTickReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val time = Calendar.getInstance().time
                viewModel.setCurrentTime(time)
            }
        }

        activity?.registerReceiver(
            timeTickReceiver,
            IntentFilter(
                Intent.ACTION_TIME_TICK
            )
        )

        viewModel.endTime.observe(viewLifecycleOwner) {
        }
    }

    private fun initTimerViewPager() {
        val items = mutableListOf<TimerPageFragment>().apply {
            for (i in 1..15) {
                add(
                    TimerPageFragment(
                        i,
                        object : OnAttachTimeListener {
                            override fun onAttachTime(time: LiveData<Date>) {
                                viewModel.attachTime(time)
                            }
                        },
                        object : OnTimerDeleteListener {
                            override fun onTimerDelete(timerPageFragment: TimerPageFragment, time: LiveData<Date>) {
                                viewModel.removeTime(time)
                                timerListAdapter.removeItem(timerPageFragment)
                            }
                        }
                    )
                )
            }
        }
        timerListAdapter = TimerPagerAdapter(
            this,
            items
        )

        binding.timerList.adapter = timerListAdapter
        binding.timerList.offscreenPageLimit = 15

        val offset = resources.getDimensionPixelOffset(R.dimen.timerItemTopMargin) + resources.getDimensionPixelOffset(R.dimen.timerItemTimeTextTopMargin)

        binding.timerList.setPageTransformer { page, position ->
            page.translationY = position * -offset
        }
    }

    override fun onReturnToPreviousScreen() {
        super.onReturnToPreviousScreen()

        activity?.unregisterReceiver(timeTickReceiver)
    }
}