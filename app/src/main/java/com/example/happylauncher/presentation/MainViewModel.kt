package com.example.happylauncher.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happylauncher.domain.usecases.BackgroundTransparentUseCase
import com.example.happylauncher.common.Transparent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val backgroundTransparentUseCase: BackgroundTransparentUseCase
): ViewModel() {
    private val _backgroundTransparentStep: MutableStateFlow<Int> =
        MutableStateFlow(Transparent.PERCENT20.transparentStep)
    val backgroundTransparentStep: StateFlow<Int> = _backgroundTransparentStep.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            backgroundTransparentUseCase.invoke { transparent ->
                _backgroundTransparentStep.update { getTransparentStep(transparent) }
            }
        }
    }

    private fun getTransparentStep(backgroundTransparentStep: Float) =
        Transparent.entries.find {
            it.transparentValue == backgroundTransparentStep
        }?.transparentStep ?: 1
}