package com.example.happylauncher.ui.favourites

import android.widget.TextClock
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.happylauncher.R
import com.example.happylauncher.base.BaseViewModel


@Composable
fun FavouritesScreen(
    viewModel: FavouritesViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    FavouritesScreenState { state }
}

@Composable
fun FavouritesScreenState(
    state: () -> BaseViewModel.ScreenState
) {
    when (val stateValue = state()) {
        is BaseViewModel.ScreenState.Success<*> -> {
            val screen = (stateValue.viewState as FavouritesScreenState)
            Column(
                modifier = Modifier.fillMaxHeight()
            ) {
                TimeWidget(timeWidgetState = screen.timeWidgetState)
                Spacer(modifier = Modifier.weight(1f))
                MenuReminderWidget()
            }
        }

        is BaseViewModel.ScreenState.Loading -> {

        }

        is BaseViewModel.ScreenState.Error -> {

        }
    }
}

@Composable
fun TimeWidget(timeWidgetState: TimeWidgetState) {
    Column {
        TextClock()
        Text(
            modifier = Modifier.padding(start = 16.dp),
            color = colorResource(id = R.color.white),
            text = timeWidgetState.dateAndBattery
        )
    }
}

@Composable
fun MenuReminderWidget() {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.swipe_to_open_menu),
            color = colorResource(id = R.color.white),
            fontSize = 16.sp
        )
    }
}

@Composable
fun TextClock() {
    AndroidView(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 24.dp
        ),
        factory = { context ->
            TextClock(context).apply {
                textSize = 64f
                setTextColor(ContextCompat.getColor(context, R.color.white))
            }
        })
}