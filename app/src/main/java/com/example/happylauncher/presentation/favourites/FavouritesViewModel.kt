package com.example.happylauncher.presentation.favourites

import com.example.happylauncher.common.base.BaseViewModel
import com.example.happylauncher.common.utils.DateUtils
import com.example.happylauncher.domain.usecases.GetBatteryLevelUseCase
import com.example.happylauncher.domain.usecases.GetDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(batteryLevel: Int) : BaseViewModel() {
    private var date = DateUtils.getDate()
    private var battery = batteryLevel

    init {
        initializeScreen()
        observeListeners()
    }

    private fun initializeScreen() {
        _viewState.update {
            ScreenState.Success(
                FavouritesScreenState(
                    FavouritesScreenState.TimeWidgetState("$date, $battery%")
                )
            )
        }
    }

    private fun observeListeners() {
        baseViewModelScope.launch {
            GetDateUseCase().invoke()?.collect {
                date = it
                initializeScreen()
            }
        }
        baseViewModelScope.launch {
            GetBatteryLevelUseCase().invoke()?.collect {
                battery = it
                initializeScreen()
            }
        }
    }
}