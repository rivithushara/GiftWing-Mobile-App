package com.myapp.giftwing.sealed

import com.myapp.giftwing.sealed.AllError as ErrorBody

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    object Success : UiState()
    class Error(val error: ErrorBody) : UiState()
}
