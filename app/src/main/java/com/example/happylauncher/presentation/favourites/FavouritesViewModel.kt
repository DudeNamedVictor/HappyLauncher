package com.example.happylauncher.presentation.favourites

import com.example.happylauncher.common.base.BaseViewModel
import com.example.happylauncher.domain.usecases.GetBatteryLevelUseCase
import com.example.happylauncher.domain.usecases.GetDateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getBatteryLevelUseCase: GetBatteryLevelUseCase
) : BaseViewModel() {

    init {
        baseViewModelScope.launch {
            initializeScreen(GetDateUseCase().invoke(), getBatteryLevelUseCase.invoke())
        }
    }

    private fun initializeScreen(date: String, battery: Int) {
        _viewState.update {
            ScreenState.Success(
                FavouritesScreenState(
                    FavouritesScreenState.TimeWidgetState("$date, $battery%")
                )
            )
        }
    }
}