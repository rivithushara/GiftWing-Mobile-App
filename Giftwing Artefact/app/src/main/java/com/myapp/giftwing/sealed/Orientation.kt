package com.myapp.giftwing.sealed

sealed class Orientation {
    object Vertical : Orientation()
    object Horizontal : Orientation()
}