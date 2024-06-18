package com.example.happylauncher.presentation.settings

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.happylauncher.common.base.BaseViewModel
import com.example.happylauncher.presentation.composecomponents.ProgressIndicator
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlin.math.roundToInt


@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    SettingsScreenState(
        state = { state },
        onChangeWallpaper = { viewModel.updateWallpaper(it) },
        onSliderChanged = { viewModel.updateTransparent(it) }
    )
}

@Composable
fun SettingsScreenState(
    state: () -> BaseViewModel.ScreenState,
    onChangeWallpaper: (Uri) -> Unit,
    onSliderChanged: (Float) -> Unit
) {
    when (val stateValue = state()) {
        is BaseViewModel.ScreenState.Success<*> -> {
            val screen = (stateValue.viewState as SettingsScreenState)
            Column(modifier = Modifier.fillMaxSize()) {
                BackgroundSettingsWidget(screen.backgroundSettingsWidget.transparentStep) { step ->
                    onSliderChanged.invoke(step)
                }
                WallpaperWidget { onChangeWallpaper.invoke(it) }
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
            .fillMaxWidth()
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

@Composable
fun WallpaperWidget(
    onChangeWallpaper: (Uri) -> Unit
) {
    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetMultipleContents()) { uri ->
            if (uri.isNotEmpty()) {
                onChangeWallpaper.invoke(uri.last())
            }
        }
    val controller = rememberColorPickerController()
    val showController = remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .border(
                width = 2.dp,
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = R.string.choose_wallpaper),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(8.dp)
                    .clickable {
                        galleryLauncher.launch("image/*")
                    }
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ),
                text = stringResource(id = R.string.from_gallery),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable {
                        showController.value = showController.value.not()
                    }
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(16.dp)
                    ),
                text = stringResource(id = R.string.fill_color),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 16.sp
            )
        }
        if (showController.value) {
            HsvColorPicker(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .padding(10.dp),
                controller = controller,
                onColorChanged = {

                }
            )
        }
    }
}