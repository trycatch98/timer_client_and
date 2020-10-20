package com.twomin.timerclient

data class Timer(
    val id: Int,
    val title: String,
    val type: TimerType
)

sealed class TimerType {
    data class HourTimer(val hour: String, val minute: String, val second: String) : TimerType()
    data class MinuteTimer(val minute: String, val second: String) : TimerType()
    data class SecondTimer(val second: String) : TimerType()
}