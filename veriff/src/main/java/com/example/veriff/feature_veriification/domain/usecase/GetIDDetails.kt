package com.example.veriff.feature_veriification.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import com.example.veriff.core.Resource
import com.example.veriff.feature_veriification.domain.model.ExtractedData
import com.example.veriff.feature_veriification.domain.repository.VerificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class GetIDDetails @Inject constructor(
    private val repository: VerificationRepository
) {

    operator fun invoke(imageFile: Uri, context: Context): Flow<Resource<ExtractedData>> = channelFlow {
        try {
            send(Resource.Loading<ExtractedData>())
            val details = repository.getDetails(imageFile, context)
            send(Resource.Success<ExtractedData>(details))
        } catch (e: Exception) {
            send(Resource.Error<ExtractedData>(e.message ?: "Unknown Error"))
        }
    }

    operator fun invoke(bitmap: Bitmap): Flow<Resource<ExtractedData>> = channelFlow {
        try {
            send(Resource.Loading<ExtractedData>())
            val details = repository.getDetails(bitmap)
            send(Resource.Success<ExtractedData>(details))
        } catch (e: Exception) {
            send(Resource.Error<ExtractedData>(e.message ?: "Unknown Error"))
        }
    }

    operator fun invoke(imageFile: File, context: Context): Flow<Resource<ExtractedData>> = channelFlow {
        try {
            send(Resource.Loading<ExtractedData>())
            val details = repository.getDetails(imageFile, context)
            send(Resource.Success<ExtractedData>(details))
        } catch (e: Exception) {
            send(Resource.Error<ExtractedData>(e.message ?: "Unknown Error"))
        }
    }

    operator fun invoke(image: Image): Flow<Resource<ExtractedData>> = channelFlow {
        try {
            send(Resource.Loading<ExtractedData>())
            val details = repository.getDetails(image)
            send(Resource.Success<ExtractedData>(details))
        } catch (e: Exception) {
            e.printStackTrace()
            send(Resource.Error<ExtractedData>(e.message ?: "Unknown Error"))
        }
    }
}
