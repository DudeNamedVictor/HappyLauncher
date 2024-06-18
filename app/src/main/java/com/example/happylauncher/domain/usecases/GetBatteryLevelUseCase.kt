package com.example.happylauncher.domain.usecases

import javax.inject.Inject


class GetBatteryLevelUseCase @Inject constructor(
    private val batteryLevel: Int
) {

    fun invoke() = batteryLevel

}