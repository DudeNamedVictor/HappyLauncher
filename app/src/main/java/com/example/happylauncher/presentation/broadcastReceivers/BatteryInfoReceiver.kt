package com.example.happylauncher.presentation.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


class BatteryInfoReceiver : BroadcastReceiver() {
    private val scope = MainScope()

    private val _batteryPercents: MutableSharedFlow<Int> = MutableSharedFlow()
    val batteryPercents: SharedFlow<Int> = _batteryPercents

    override fun onReceive(context: Context, intent: Intent) {
        val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
        scope.launch { _batteryPercents.emit(level) }
    }

    fun clearScope() {
        scope.cancel()
    }
}