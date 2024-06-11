package com.example.happylauncher.ui.allapps

import android.os.UserHandle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.happylauncher.BaseViewModel
import com.example.happylauncher.R


@Composable
fun AllAppsScreen(
    viewModel: AllAppsViewModel = hiltViewModel()
) {
    val state by viewModel.viewState.collectAsState()

    AllAppsScreenState({
        state
    }, { searchText ->
        viewModel.searchByName(searchText)
    }, { packageName, userHandle ->
        viewModel.launchApp(packageName = packageName, userHandle = userHandle)
    })
}

@Composable
fun AllAppsScreenState(
    state: () -> BaseViewModel.ScreenState,
    onSearchClick: (String) -> Unit,
    onAppClick: (String, UserHandle) -> Unit
) {
    when (val stateValue = state()) {
        is BaseViewModel.ScreenState.Success<*> -> {
            val apps = (stateValue.viewState as AllAppsScreenState).apps
            val focusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current
            var searchValue by remember { mutableStateOf("") }
            Column {
                Row {
                    CommonTextFiled(
                        text = searchValue,
                        onValueChange = {
                            searchValue = it
                            onSearchClick.invoke(it)
                        },
                        onClearClicked = {
                            searchValue = ""
                            onSearchClick.invoke("")
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    )
                }
                AppsList(apps) { packageName, userHandle ->
                    onAppClick(packageName, userHandle)
                }
            }
        }

        is BaseViewModel.ScreenState.Loading -> {

        }

        is BaseViewModel.ScreenState.Error -> {

        }
    }
}

@Composable
fun AppsList(
    apps: List<AppUIState>,
    onAppClick: (String, UserHandle) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxWidth()
    ) {
        items(apps) { app ->
            Row(
                Modifier
                    .padding(
                        horizontal = 12.dp,
                        vertical = 4.dp
                    )
                    .clickable {
                        onAppClick(app.packageName, app.user)
                    }
            ) {
                Card(
                    modifier = Modifier.size(36.dp),
                    shape = CircleShape
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(app.icon),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    text = app.appLabel
                )
            }
        }
    }
}

@Composable
fun CommonTextFiled(
    text: String = "",
    onValueChange: (String) -> Unit,
    onClearClicked: () -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        label = {
            Text(
                stringResource(
                    id = R.string.search
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
            Icon(
                modifier = Modifier.clickable { onClearClicked() },
                imageVector = Icons.Default.Clear,
                contentDescription = null
            )
        }
    )
}