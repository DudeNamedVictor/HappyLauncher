package com.example.happylauncher.ui.favourites

import com.example.happylauncher.base.BaseViewModel
import com.example.happylauncher.ui.BroadcastsRepository
import com.example.happylauncher.utils.getDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val batteryLevel: Int
) : BaseViewModel() {
    private var batteryPercents: Int? = null
    private var formatDate: String? = null

    init {
        baseViewModelScope.launch {
            BroadcastsRepository.formatDate?.collect { date ->
                formatDate = date
                initializeScreen()
            }
        }
        baseViewModelScope.launch {
            BroadcastsRepository.batteryPercents?.collect { battery ->
                batteryPercents = battery
                initializeScreen()
            }
        }
    }

    private fun initializeScreen() {
        val date = formatDate ?: getDate()
        val battery = batteryPercents?: batteryLevel

        _viewState.update {
            ScreenState.Success(
                FavouritesScreenState(
                    TimeWidgetState("$date, $battery%")
                )
            )
        }
    }
}