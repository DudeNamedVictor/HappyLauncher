package com.example.happylauncher.ui.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.happylauncher.utils.getDate
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch


class DateReceiver : BroadcastReceiver() {
    private val scope = MainScope()

    private val _formatDate: MutableSharedFlow<String> = MutableSharedFlow()
    val formatDate: SharedFlow<String> = _formatDate
    override fun onReceive(context: Context, intent: Intent) {
        scope.launch { _formatDate.emit(getDate()) }
    }

    fun clearScope() {
        scope.cancel()
    }
}