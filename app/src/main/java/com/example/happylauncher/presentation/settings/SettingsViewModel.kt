package com.example.happylauncher.presentation.settings

import android.net.Uri
import com.example.happylauncher.common.base.BaseViewModel
import com.example.happylauncher.domain.usecases.BackgroundTransparentUseCase
import com.example.happylauncher.domain.usecases.WallpaperUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val backgroundTransparentUseCase: BackgroundTransparentUseCase,
    private val wallpaperUseCase: WallpaperUseCase
): BaseViewModel() {

    init {
        initializeScreen()
    }

    private fun initializeScreen() {
        baseViewModelScope.launch {
            backgroundTransparentUseCase.invoke { transparent ->
                _viewState.update {
                    ScreenState.Success(
                        SettingsScreenState(SettingsScreenState.TransparentWidgetState(transparent))
                    )
                }
            }
        }
    }

    fun updateTransparent(step: Float) {
        baseViewModelScope.launch { backgroundTransparentUseCase.setTransparent(step) }
    }

    fun updateWallpaper(uri: Uri) {
        baseViewModelScope.launch(Dispatchers.Default) { wallpaperUseCase.updateWallpaper(uri) }
    }
}