package com.myapp.giftwing.models

import androidx.annotation.DrawableRes

data class OnboardItem(
    @DrawableRes val image: Int,
    val text: String,
)
