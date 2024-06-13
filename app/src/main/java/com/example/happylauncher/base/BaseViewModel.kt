package com.example.happylauncher.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus


abstract class BaseViewModel : ViewModel() {
    protected val _viewState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.Loading)
    val viewState: StateFlow<ScreenState> = _viewState.asStateFlow()

    private val _errorMessage: MutableSharedFlow<Int> = MutableSharedFlow()
    val errorMessage: SharedFlow<Int> = _errorMessage

    protected val baseViewModelScope =
        viewModelScope + Dispatchers.IO + CoroutineExceptionHandler { _, _ ->
            _viewState.value = ScreenState.Error
        }

    fun showError(message: Int) {
        baseViewModelScope.launch {
            _errorMessage.emit(message)
        }
    }

    sealed class ScreenState {
        data class Success<T>(val viewState: T) : ScreenState()
        data object Loading : ScreenState()
        data object Error : ScreenState()
    }
}