package com.example.veriff.feature_veriification.presentation.viewmodels

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veriff.core.Resource
import com.example.veriff.feature_veriification.domain.usecase.GetIDDetails
import com.example.veriff.feature_veriification.domain.usecase.VerifyFaceImageUsecase
import com.example.veriff.feature_veriification.presentation.states.ResultState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class SDKMainViewModel @Inject constructor(
    private val getDetails: GetIDDetails,
    private val verifyUseCase: VerifyFaceImageUsecase
) : ViewModel() {

    private val _state = mutableStateOf(ResultState())
    val state: State<ResultState> = _state

    private fun startExtractDetailsOnly(bitmap: Bitmap, canContinue: Boolean = false) {
        getDetails(bitmap).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = canContinue,
                        detailsText = resource.data?.data?.last()?.get("data")
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = true,
                        errorMessage = resource.message ?: ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun verifyDetailsOnly(bitmap: Bitmap) {
        verifyUseCase(bitmap).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true
                    )
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        imageResult = resource.data
                    )
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = true,
                        errorMessage = resource.message ?: ""
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getDetailsAndVerify(bitmap: Bitmap) {
        startExtractDetailsOnly(bitmap, true).also {
            verifyDetailsOnly(bitmap)
        }
    }

    fun setImage(bitmap: Bitmap?) {
        bitmap?.let { getDetailsAndVerify(it) }
    }
}
