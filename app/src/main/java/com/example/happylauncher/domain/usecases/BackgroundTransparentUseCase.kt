package com.example.happylauncher.domain.usecases

import com.example.happylauncher.data.repositories.DataStoreRepository
import com.example.happylauncher.data.repositories.DataStoreRepository.Companion.TRANSPARENT
import javax.inject.Inject


class BackgroundTransparentUseCase @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) {

    suspend fun invoke(onGetTransparent: (Float) -> Unit) {
        dataStoreRepository.getData(TRANSPARENT) {
            onGetTransparent.invoke(it ?: DEFAULT_BACKGROUND_TRANSPARENT)
        }
    }

    suspend fun setTransparent(step: Float) {
        dataStoreRepository.update(step)
    }

    companion object {
        const val DEFAULT_BACKGROUND_TRANSPARENT = 2f
    }
}