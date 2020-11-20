package com.twomin.timerclient.ui.timeset.create

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.twomin.timerclient.TimeSet
import com.twomin.timerclient.TimeSetType
import com.twomin.timerclient.common.addSourceList
import java.text.SimpleDateFormat
import java.util.*

class CreateTimeSetViewModel @ViewModelInject constructor() : ViewModel() {
    private val currentTime: MutableLiveData<Long> = MutableLiveData(Date().time)

    private val totalTime: MediatorLiveData<Long> = MediatorLiveData()

    private val _endTime: MediatorLiveData<Date> = MediatorLiveData()
    val endTime: LiveData<String> = _endTime.map {
        SimpleDateFormat("a hh시 mm분 종료예정", Locale.KOREA).format(it)
    }

    init {
        _endTime.addSource(currentTime) { currentTime ->
            val totalTime = totalTime.value ?: 0
            _endTime.value = Date(currentTime + totalTime)
        }

        _endTime.addSource(totalTime) { totalTime ->
            val currentTime = currentTime.value ?: 0
            _endTime.value = Date(currentTime + totalTime)
        }

    }

    fun setCurrentTime(date: Date) {
        currentTime.value = date.time
    }

    fun attachTime(time: LiveData<Date>) {
        totalTime.removeSource(time)
        totalTime.addSource(time) {
            val total = totalTime.value ?: 0
            totalTime.value = total + it.time
        }
    }

    fun removeTime(time: LiveData<Date>) {
        val total = totalTime.value ?: 0
        val removeTime = time.value?.time ?: 0

        totalTime.value = total - removeTime
    }
}