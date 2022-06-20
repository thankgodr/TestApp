package com.example.veriff.feature_veriification.presentation.states

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResultState(
    val isLoading: Boolean = true,
    val error: Boolean = false,
    val errorMessage: String = "",
    val detailsText: String? = null,
    val imageResult: Bitmap? = null,
    val completed: Boolean = false
): Parcelable
