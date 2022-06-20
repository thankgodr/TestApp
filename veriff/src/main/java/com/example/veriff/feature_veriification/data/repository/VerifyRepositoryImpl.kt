package com.example.veriff.feature_veriification.data.repository

import android.content.Context
import android.graphics.*
import android.media.Image
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.veriff.feature_veriification.domain.model.ExtractedData
import com.example.veriff.feature_veriification.domain.repository.VerificationRepository
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import javax.inject.Inject

class VerifyRepositoryImpl @Inject constructor(
    private val recognizer: TextRecognizer,
    private val faceDetector: FaceDetector
) : VerificationRepository {

    private lateinit var image: InputImage

    override suspend fun getDetails(bitmap: Bitmap): ExtractedData {
        image = InputImage.fromBitmap(bitmap, 0)
        return process()
    }

    override suspend fun getDetails(imageFile: File, context: Context): ExtractedData {
        image = InputImage.fromFilePath(context, imageFile.toUri())
        return process()
    }

    override suspend fun getDetails(imagefile: Image): ExtractedData {
        image = InputImage.fromMediaImage(imagefile, 0)
        return process()
    }

    override suspend fun getDetails(imageUri: Uri, context: Context): ExtractedData {
        image = InputImage.fromFilePath(context, imageUri)
        return process()
    }

    override suspend fun faceMatched(id: Bitmap): Bitmap {
        image = InputImage.fromBitmap(id, 0)
        return processFace(id)
    }

    override suspend fun faceMatched(id: File, context: Context): Bitmap {
        image = InputImage.fromFilePath(context, id.toUri())
        val bitmap = BitmapFactory.decodeFile(id.absolutePath)
        return processFace(bitmap)
    }

    override suspend fun faceMatched(id: Uri, context: Context): Bitmap {
        image = InputImage.fromFilePath(context, id)
        val contentResolver = context.contentResolver
        val bitmap = try {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, id)
            } else {
                val source = ImageDecoder.createSource(contentResolver, id)
                ImageDecoder.decodeBitmap(source)
            }
        } catch (e: Exception) {
            throw e
        }
        return processFace(bitmap)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private suspend fun processFace(bitmap: Bitmap): Bitmap {
        return suspendCancellableCoroutine { cont ->
            faceDetector.process(image)
                .addOnSuccessListener { faces ->
                    if (faces.size > 1) {
                        throw Exception("More than one Face")
                    }
                    val face = faces.last()
                    val myRectPain = Paint()
                    myRectPain.setStrokeWidth(10F)
                    myRectPain.color = Color.RED
                    myRectPain.setStyle(Paint.Style.STROKE)

                    val tempBitmap = Bitmap.createBitmap(
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        Bitmap.Config.RGB_565
                    )
                    val tempCanvas = Canvas(tempBitmap)
                    tempCanvas.drawBitmap(bitmap, 0f, 0f, null)
                    tempCanvas.drawRect(face.boundingBox, myRectPain)
                    cont.resume(tempBitmap, null)
                }
                .addOnFailureListener {
                    throw it
                }
        }
    }

    private suspend fun process(): ExtractedData {
        return suspendCancellableCoroutine { cont ->
            recognizer.process(image)
                .addOnSuccessListener {
                    val data = ExtractedData(listOf(mapOf(Pair("data", it.text))))
                    cont.resume(data, null)
                }
                .addOnFailureListener {
                    throw it
                }
        }
    }
}
