package com.twomin.timerclient.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.twomin.timerclient.reverseHideAnimation
import com.twomin.timerclient.startHideAnimation
import kotlinx.coroutines.*

class FloatingBehavior(context: Context?, attrs: AttributeSet?) :
    CoordinatorLayout.Behavior<TaskLayout>(context, attrs) {

    private var job: Job? = null
    private var lastScroll = 0

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TaskLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int,
        consumed: IntArray
    ) {
        if (dyConsumed > 0) {
            hide(child)
            lastScroll = target.scrollY
        }
        else {
            if (lastScroll - target.scrollY > 100)
                show(child)
        }

    }

    private fun hide(taskLayout: TaskLayout) {
        taskLayout.startHideAnimation()
    }

    private fun show(taskLayout: TaskLayout) {
        taskLayout.reverseHideAnimation()
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TaskLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        job?.cancel()
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TaskLayout,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return true
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: TaskLayout,
        target: View,
        type: Int
    ) {
        job?.cancel()
        job = GlobalScope.launch {
            delay(500)
            MainScope().launch {
                show(child)
            }
        }
    }
}