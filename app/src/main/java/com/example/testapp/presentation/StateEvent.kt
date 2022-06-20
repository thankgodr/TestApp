package com.example.testapp.presentation

import android.graphics.Bitmap

sealed class StateEvent(val id: Bitmap? = null) {
    class GetDetailsEvent(id: Bitmap) : StateEvent(id)
    class VerifyImageEvent() : StateEvent()
}
