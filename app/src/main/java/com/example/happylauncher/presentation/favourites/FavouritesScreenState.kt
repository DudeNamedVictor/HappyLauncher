package com.example.happylauncher.presentation.favourites


data class FavouritesScreenState(val timeWidgetState: TimeWidgetState) {

    data class TimeWidgetState(val dateAndBattery: String)

}