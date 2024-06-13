package com.example.happylauncher.ui

import kotlinx.coroutines.flow.SharedFlow


object BroadcastsRepository {

    var formatDate: SharedFlow<String>? = null
    var batteryPercents: SharedFlow<Int>? = null

    fun addDataSource(formatDate: SharedFlow<String>) {
        this.formatDate = formatDate
    }

    fun addBatterySource(batteryPercents: SharedFlow<Int>) {
        this.batteryPercents = batteryPercents
    }

}