package com.example.testapp.presentation

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.veriff.core.Resource
import com.example.veriff.feature_veriification.VerifySDK
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel : ViewModel() {
    private val _status = MutableLiveData(VerificationState())
    val state: LiveData<VerificationState> = _status

    private  var bitmapID: Bitmap? = null
    fun handleEvent(event: StateEvent) {
        when (event) {
            is StateEvent.GetDetailsEvent -> {
                event.id?.let {
                    bitmapID = it
                    extractDetails(it)
                }
            }
            is StateEvent.VerifyImageEvent -> {
                bitmapID?.let {
                    verifyImages(it)
                }
                if (bitmapID == null) {
                    _status.postValue(
                        _status.value?.copy(
                            isFaceDetectionLoading = false,
                            error = true,
                            errorMessage = "Please get ID details first"
                        )
                    )
                }
            }
        }
    }

    private fun verifyImages(bitmap: Bitmap) {
        VerifySDK.verifyFaceId(bitmap).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _status.postValue(
                        _status.value?.copy(
                            isFaceDetectionLoading = true
                        )
                    )
                }
                is Resource.Success -> {
                    _status.postValue(
                        _status.value?.copy(
                            isFaceDetectionLoading = false,
                            detectedImage = resource.data
                        )
                    )
                }
                is Resource.Error -> {
                    _status.postValue(
                        _status.value?.copy(
                            isFaceDetectionLoading = false,
                            error = true,
                            errorMessage = resource.message ?: "Unkown Error"
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun extractDetails(bitmap: Bitmap) {
        VerifySDK.getIdDetails(bitmap).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _status.postValue(
                        _status.value?.copy(
                            isTextLoading = true
                        )
                    )
                }
                is Resource.Success -> {
                    _status.postValue(
                        resource.data?.data?.last()?.get("data")
                            ?.let {
                                _status.value?.copy(
                                    isTextLoading = false, result = it
                                ) ?: VerificationState(isTextLoading = false, result = it)
                            }
                    )
                }
                is Resource.Error -> {
                    _status.postValue(
                        _status.value?.copy(
                            isTextLoading = false,
                            error = true,
                            errorMessage = resource.message ?: "Unknown Error"
                        )
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}
