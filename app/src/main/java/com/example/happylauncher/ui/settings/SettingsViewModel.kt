package com.example.happylauncher.ui.settings

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import com.example.happylauncher.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsDataStore: DataStore<Preferences>
): BaseViewModel() {
    private val transparent: Channel<Float> = Channel()

    init {
        _viewState.update { ScreenState.Loading }
        getTransparentStep()
        initializeScreen()
    }

    private fun getTransparentStep() {
        baseViewModelScope.launch {
            settingsDataStore.data.collect {
                transparent.send(it[TRANSPARENT] ?: DEFAULT_BACKGROUND_TRANSPARENT)
            }
        }
    }

    private fun initializeScreen() {
        baseViewModelScope.launch {
            val a = transparent.receive()
            _viewState.update {
                ScreenState.Success(SettingsScreenState(TransparentWidgetState(a)))
            }
        }
    }

    fun updateTransparent(transparentStep: Float) {
        baseViewModelScope.launch {
            settingsDataStore.edit { it[TRANSPARENT] = transparentStep }
        }
        _viewState.update {
            ScreenState.Success(SettingsScreenState(TransparentWidgetState(transparentStep)))
        }
    }

    companion object {
        val TRANSPARENT = floatPreferencesKey("transparent")
        const val DEFAULT_BACKGROUND_TRANSPARENT = 2f
    }

}