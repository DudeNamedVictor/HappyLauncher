package com.example.happylauncher

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.happylauncher.ui.settings.SettingsViewModel
import com.example.happylauncher.ui.settings.SettingsViewModel.Companion.TRANSPARENT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>
): ViewModel() {
    private val _backgroundTransparentStep: MutableStateFlow<Int> =
        MutableStateFlow(Transparent.PERCENT20.transparentStep)
    val backgroundTransparentStep: StateFlow<Int> = _backgroundTransparentStep.asStateFlow()

    init {
        viewModelScope.launch {
            settingsDataStore.data.collect {
                val backgroundTransparentStep =
                    it[TRANSPARENT] ?: SettingsViewModel.DEFAULT_BACKGROUND_TRANSPARENT
                _backgroundTransparentStep.update { getTransparentStep(backgroundTransparentStep) }
            }
        }
    }

    private fun getTransparentStep(backgroundTransparentStep: Float) =
        Transparent.entries.filter { it.transparentValue == backgroundTransparentStep }[0].transparentStep

    enum class Transparent(val transparentStep: Int, val transparentValue: Float) {
        PERCENT100(R.color.black, 10f),
        PERCENT90(R.color.transparent90, 9f),
        PERCENT80(R.color.transparent80, 8f),
        PERCENT70(R.color.transparent70, 7f),
        PERCENT60(R.color.transparent60, 6f),
        PERCENT50(R.color.transparent50, 5f),
        PERCENT40(R.color.transparent40, 4f),
        PERCENT30(R.color.transparent30, 3f),
        PERCENT20(R.color.transparent20, 2f),
        PERCENT10(R.color.transparent10, 1f)
    }
}