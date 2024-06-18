package com.example.happylauncher.domain.usecases

import com.example.happylauncher.data.repositories.BroadcastsRepository


class GetDateUseCase {

    fun invoke() = BroadcastsRepository.formatDate

}