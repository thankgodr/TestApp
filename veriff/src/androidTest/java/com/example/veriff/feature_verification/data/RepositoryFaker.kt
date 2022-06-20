package com.example.veriff.feature_verification.data

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import com.example.veriff.feature_veriification.domain.model.ExtractedData
import com.example.veriff.feature_veriification.domain.repository.VerificationRepository
import java.io.File

class RepositoryFaker : VerificationRepository {
    override suspend fun getDetails(bitmap: Bitmap): ExtractedData {
        return ExtractedData(data = listOf(mapOf(Pair("tag", "value"))))
    }

    override suspend fun getDetails(imageFile: File, context: Context): ExtractedData {
        return ExtractedData(data = listOf(mapOf(Pair("tag", "value"))))
    }

    override suspend fun getDetails(image: Image): ExtractedData {
        return ExtractedData(data = listOf(mapOf(Pair("tag", "value"))))
    }

    override suspend fun getDetails(imageUri: Uri, context: Context): ExtractedData {
        return ExtractedData(data = listOf(mapOf(Pair("tag", "value"))))
    }

    override suspend fun faceMatched(id: Bitmap): Bitmap {
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
    }

    override suspend fun faceMatched(id: File, context: Context): Bitmap {
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
    }

    override suspend fun faceMatched(id: Uri, context: Context): Bitmap {
        return Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8)
    }
}