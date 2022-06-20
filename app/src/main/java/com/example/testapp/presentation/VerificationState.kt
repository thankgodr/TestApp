package com.example.testapp.presentation

import android.graphics.Bitmap

data class VerificationState(
    val isTextLoading: Boolean = true,
    val isFaceDetectionLoading: Boolean = true,
    val error: Boolean = false,
    val errorMessage: String = "",
    val result: String = "",
    val detectedImage: Bitmap? = null
)
