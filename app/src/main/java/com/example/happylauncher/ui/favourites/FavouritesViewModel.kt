package com.example.happylauncher.ui.favourites

import com.example.happylauncher.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val batteryLevel: Int
) : BaseViewModel() {

    init {
        initializeScreen()
    }

    private fun initializeScreen() {
        _viewState.update {
            ScreenState.Success(
                FavouritesScreenState(
                    TimeWidgetState(getDateAndBattery())
                )
            )
        }
    }

    private fun getDateAndBattery(): String {
        val dateFormat = SimpleDateFormat(DATE_FORMATTER, Locale.getDefault())
        val date = dateFormat.format(Date()).replace(DATE_REPLACE_VALUE, "")

        return "$date, $batteryLevel%"
    }

    companion object {
        private const val DATE_FORMATTER = "EEE, d MMM"
        private const val DATE_REPLACE_VALUE = "."
    }

}