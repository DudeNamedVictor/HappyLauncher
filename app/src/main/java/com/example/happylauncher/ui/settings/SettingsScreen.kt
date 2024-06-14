package com.example.happylauncher.ui.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.happylauncher.R
import com.example.happylauncher.base.BaseViewModel
import com.example.happylauncher.ui.composecomponents.ProgressIndicator
import kotlin.math.roundToInt


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    SettingsScreenState({ state }, { viewModel.updateTransparent(it) })
}

@Composable
fun SettingsScreenState(
    state: () -> BaseViewModel.ScreenState,
    onSliderChanged: (Float) -> Unit
) {
    when (val stateValue = state()) {
        is BaseViewModel.ScreenState.Success<*> -> {
            val screen = (stateValue.viewState as SettingsScreenState)
            BackgroundSettingsWidget(screen.backgroundSettingsWidget.transparentStep) {
                onSliderChanged.invoke(it)
            }
        }

        is BaseViewModel.ScreenState.Loading -> {
            ProgressIndicator()
        }

        is BaseViewModel.ScreenState.Error -> {

        }
    }
}

@Composable
fun BackgroundSettingsWidget(
    transparent: Float,
    onSliderChanged: (Float) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var sliderPosition by remember { mutableFloatStateOf(transparent) }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.background_transparent),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Slider(
                value = sliderPosition,
                onValueChange = {
                    sliderPosition = it
                    onSliderChanged.invoke(sliderPosition.roundToInt().toFloat())
                },
                colors = SliderDefaults.colors(
                    thumbColor = Color.White,
                    activeTrackColor = Color.White,
                    inactiveTrackColor = Color.White,
                ),
                steps = 8,
                valueRange = 1f..10f
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = sliderPosition.roundToInt().toString(),
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}