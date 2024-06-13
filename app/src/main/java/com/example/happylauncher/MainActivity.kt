package com.example.happylauncher

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.happylauncher.ui.BroadcastsRepository
import com.example.happylauncher.ui.allapps.AllAppsScreen
import com.example.happylauncher.ui.broadcastReceivers.BatteryInfoReceiver
import com.example.happylauncher.ui.broadcastReceivers.DateReceiver
import com.example.happylauncher.ui.favourites.FavouritesScreen
import com.example.happylauncher.ui.theme.HappyLauncherTheme
import com.example.happylauncher.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val dateReceiver: DateReceiver by lazy { DateReceiver() }
    private val batteryInfoReceiver: BatteryInfoReceiver by lazy { BatteryInfoReceiver() }

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyLauncherTheme {
                val pagerState = rememberPagerState { 2 }
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { MainScreenIndicator(pagerState) }) { innerPadding ->
                    Column(
                        Modifier.padding(innerPadding)
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxWidth()
                        ) { page ->
                            when (page) {
                                MAIN_SCREEN -> {
                                    FavouritesScreen()
                                }

                                MENU_SCREEN -> {
                                    AllAppsScreen{ message ->
                                        lifecycleScope.launch {
                                            message.collect { message ->
                                                showToast(resources.getString(message))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        BroadcastsRepository.addDataSource(dateReceiver.formatDate)
        BroadcastsRepository.addBatterySource(batteryInfoReceiver.batteryPercents)
        registerReceiver(dateReceiver, IntentFilter(IntentFilter().apply {
            addAction(Intent.ACTION_TIME_TICK)
            addAction(Intent.ACTION_TIME_CHANGED)
            addAction(Intent.ACTION_TIMEZONE_CHANGED)
            addAction(Intent.ACTION_TIME_CHANGED)
        }))
        registerReceiver(batteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onPause() {
        super.onPause()
        batteryInfoReceiver.clearScope()
        dateReceiver.clearScope()
        unregisterReceiver(dateReceiver)
        unregisterReceiver(batteryInfoReceiver)
    }

    companion object {
        private const val MAIN_SCREEN = 0
        private const val MENU_SCREEN = 1
    }
}

@ExperimentalFoundationApi
@Composable
fun MainScreenIndicator(pagerState: PagerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(16.dp)
            )
        }
    }
}