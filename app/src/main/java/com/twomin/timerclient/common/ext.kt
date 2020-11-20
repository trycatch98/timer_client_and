package com.twomin.timerclient.common

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

fun <T> MediatorLiveData<T>.addSourceList(vararg liveData: MutableLiveData<*>, onChanged: () -> T) {
    liveData.forEach {
        addSource(it) {
            value = onChanged()
        }
    }
}