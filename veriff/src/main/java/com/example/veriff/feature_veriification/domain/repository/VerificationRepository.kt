package com.example.veriff.feature_veriification.domain.repository

import android.content.Context
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import com.example.veriff.feature_veriification.domain.model.ExtractedData
import java.io.File

interface VerificationRepository {
    suspend fun getDetails(bitmap: Bitmap): ExtractedData
    suspend fun getDetails(imageFile: File, context: Context): ExtractedData
    suspend fun getDetails(image: Image): ExtractedData
    suspend fun getDetails(imageUri: Uri, context: Context): ExtractedData

    suspend fun faceMatched(id: Bitmap): Bitmap
    suspend fun faceMatched(id: File, context: Context): Bitmap
    suspend fun faceMatched(id: Uri, context: Context): Bitmap
}
