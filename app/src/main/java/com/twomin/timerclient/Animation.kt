package com.twomin.timerclient

import android.animation.ValueAnimator
import androidx.core.view.children
import com.twomin.timerclient.ui.common.TaskLayout

fun TaskLayout.startHideAnimation() {
    val taskLayout = this
    if ((tag as? ValueAnimator) != null)
        return
    tag = ValueAnimator.ofFloat(1f, 0f).apply {
        duration = 450
        val height = taskLayout.height
        addUpdateListener {
            val value = it.animatedValue as Float
            taskLayout.children.forEach { view ->
                view.alpha = value
                view.translationY = height * (1f - value)
            }
        }
        start()
    }
}

fun TaskLayout.reverseHideAnimation() {
    (tag as? ValueAnimator)?.reverse()
    tag = null
}