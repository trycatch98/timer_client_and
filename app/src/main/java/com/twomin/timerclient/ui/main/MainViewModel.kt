package com.twomin.timerclient.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.twomin.timerclient.TimeSet
import com.twomin.timerclient.TimeSetType
import com.twomin.timerclient.common.Event

class MainViewModel @ViewModelInject constructor() : ViewModel() {
    private val _recentTimeSet: MutableLiveData<List<TimeSet>> = MutableLiveData()
    val recentTimeSet: LiveData<List<TimeSet>> get() = _recentTimeSet

    private val _saveTimeSet: MutableLiveData<List<TimeSet>> = MutableLiveData()
    val saveTimeSet: LiveData<List<TimeSet>> get() = _saveTimeSet

    private val _presetTimeSet: MutableLiveData<List<TimeSet>> = MutableLiveData()
    val presetTimeSet: LiveData<List<TimeSet>> get() = _presetTimeSet
    
    private val _createTimeSet: MutableLiveData<Event<Unit>> = MutableLiveData()
    val createTimeSet: LiveData<Event<Unit>> get() = _createTimeSet

    init {
        val timeSet = listOf(
            TimeSet(
                0,
                "타이머명",
                TimeSetType.HourTimeSet("99", "99", "99")
            ),
            TimeSet(
                1,
                "타이머명",
                TimeSetType.MinuteTimeSet("99", "99")
            ),
            TimeSet(
                2,
                "타이머명",
                TimeSetType.SecondTimeSet("99")
            )
        )


        _recentTimeSet.value = timeSet
        _saveTimeSet.value = timeSet
        _presetTimeSet.value = timeSet
    }

    fun deleteTimeSet(timeSet: TimeSet) {

    }

    fun hideTimeSet(timeSet: TimeSet) {

    }

    fun manageTimeSet() {

    }

    fun clickTimeSet(timeSet: TimeSet) {

    }

    fun createTimeSet() {
        _createTimeSet.value = Event(Unit)
    }

    fun clickPreSet() {

    }

    fun clickHistory() {

    }

    fun clickSetting() {

    }
}