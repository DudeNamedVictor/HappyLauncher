package com.example.happylauncher.presentation.settings


data class SettingsScreenState(val backgroundSettingsWidget: TransparentWidgetState) {

    data class TransparentWidgetState(val transparentStep: Float)
}