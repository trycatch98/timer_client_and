package com.twomin.timerclient

data class TimeSet(
    val id: Int,
    val title: String,
    val type: TimeSetType
)

sealed class TimeSetType {
    data class HourTimeSet(val hour: String, val minute: String, val second: String) : TimeSetType()
    data class MinuteTimeSet(val minute: String, val second: String) : TimeSetType()
    data class SecondTimeSet(val second: String) : TimeSetType()
}