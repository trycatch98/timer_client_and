package com.twomin.timerclient.ui.timeset.create

import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.observe
import com.twomin.timerclient.R
import com.twomin.timerclient.databinding.FragmentTimerBinding
import com.twomin.timerclient.ui.common.BaseFragment

class TimerPageFragment(
    private val timerNumber: Int,
    private val onAttachTimeListener: CreateTimeSetFragment.OnAttachTimeListener,
    private val onTimerDeleteListener: CreateTimeSetFragment.OnTimerDeleteListener
) : BaseFragment<TimerPageViewModel, FragmentTimerBinding>(
    R.layout.fragment_timer
) {

    override val viewModel by viewModels<TimerPageViewModel>()

    override fun initView() {
        super.initView()
        viewModel.setTimerNumber(timerNumber)
    }

    override fun initObserve() {
        super.initObserve()

        onAttachTimeListener.onAttachTime(viewModel.time)

        viewModel.deleteEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                onTimerDeleteListener.onTimerDelete(this, viewModel.time)
            }
        }
    }
}