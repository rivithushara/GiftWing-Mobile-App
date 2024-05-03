package com.myapp.giftwing.providers

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalNavHost =
    compositionLocalOf<NavHostController>{ error("Error!!!!") }