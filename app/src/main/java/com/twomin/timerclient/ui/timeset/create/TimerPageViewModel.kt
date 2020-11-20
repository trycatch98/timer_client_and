package com.twomin.timerclient.ui.timeset.create

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.twomin.timerclient.common.Event
import java.util.*

class TimerPageViewModel @ViewModelInject constructor() : ViewModel() {
    companion object {
        const val MAX_INPUT_TIME = 362439
    }

    private val _time: MutableLiveData<Date> = MutableLiveData()
    val time: LiveData<Date> get() = _time

    private val _inputTime: MutableLiveData<String> = MutableLiveData()
    val inputTime: LiveData<String> get() = _inputTime

    private val _timerNumber: MutableLiveData<Int> = MutableLiveData()
    val timerNumber: LiveData<Int> get() = _timerNumber

    private val _deleteEvent: MutableLiveData<Event<Unit>> = MutableLiveData()
    val deleteEvent: LiveData<Event<Unit>> get() = _deleteEvent

    fun clickNumber(number: Int) {
        var input = _inputTime.value ?: ""
        input += number
        if (input.toInt() > MAX_INPUT_TIME)
            input = MAX_INPUT_TIME.toString()
        _inputTime.value = input
    }

    fun convertHour() {
        val input = _inputTime.value?.toLong() ?: return
        _time.value = Date(input * 60 * 60 * 1000)
    }

    fun convertMinute() {

    }

    fun convertSecond() {

    }

    fun setTimerNumber(number: Int) {
        _timerNumber.value = number
    }

    fun delete() {
        _deleteEvent.value = Event(Unit)
    }
}