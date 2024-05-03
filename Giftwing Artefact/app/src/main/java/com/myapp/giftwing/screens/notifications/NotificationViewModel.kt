package com.myapp.giftwing.screens.notifications


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.myapp.giftwing.sealed.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * A View model with hiltViewModel annotation that is used to access this view model everywhere needed
 */
@HiltViewModel
class NotificationViewModel @Inject constructor() : ViewModel() {
    val uiState = mutableStateOf<UiState>(UiState.Idle)

}