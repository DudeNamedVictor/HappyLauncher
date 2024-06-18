package com.example.happylauncher.domain.usecases

import com.example.happylauncher.data.repositories.BroadcastsRepository


class GetBatteryLevelUseCase {

    fun invoke() = BroadcastsRepository.batteryPercents

}