package com.example.happylauncher.data.repositories

import kotlinx.coroutines.flow.SharedFlow


object BroadcastsRepository {

    var formatDate: SharedFlow<String>? = null
    var batteryPercents: SharedFlow<Int>? = null

}