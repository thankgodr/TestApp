package com.example.veriff.feature_verification.di

import android.content.Context
import com.example.veriff.feature_verification.data.RepositoryFaker
import com.example.veriff.feature_veriification.di.AppModule
import com.example.veriff.feature_veriification.domain.repository.VerificationRepository
import com.google.mlkit.common.sdkinternal.MlKitContext
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module

@Module
class FakeAppModule : AppModule() {

    override fun provideRepository(
        faceDetector: FaceDetector,
        textRecognizer: TextRecognizer
    ): VerificationRepository {
        return RepositoryFaker()
    }
}
