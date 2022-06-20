package com.example.veriff.feature_veriification.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.example.veriff.core.Resource
import com.example.veriff.feature_veriification.domain.repository.VerificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class VerifyFaceImageUsecase @Inject constructor(
    private val verificationRepository: VerificationRepository
) {
    operator fun invoke(bitmapID: Bitmap): Flow<Resource<Bitmap>> = channelFlow { try {
        send(Resource.Loading<Bitmap>())
        val result = verificationRepository.faceMatched(bitmapID)
        send(Resource.Success<Bitmap>(result))
    } catch (e: Exception) {
            send(Resource.Error<Bitmap>(e.message ?: "Unknown Error"))
        }
    }

    operator fun invoke(fileID: File, context: Context): Flow<Resource<Bitmap>> = channelFlow {
        try {
            send(Resource.Loading<Bitmap>())
            val result = verificationRepository.faceMatched(fileID, context)
            send(Resource.Success<Bitmap>(result))
        } catch (e: Exception) {
            send(Resource.Error<Bitmap>(e.message ?: "Unknown Error"))
        }
    }

    operator fun invoke(uriID: Uri, context: Context): Flow<Resource<Bitmap>> = channelFlow {
        try {
            send(Resource.Loading<Bitmap>())
            val result = verificationRepository.faceMatched(uriID, context)
            send(Resource.Success<Bitmap>(result))
        } catch (e: Exception) {
            send(Resource.Error<Bitmap>(e.message ?: "Unknown Error"))
        }
    }
}
