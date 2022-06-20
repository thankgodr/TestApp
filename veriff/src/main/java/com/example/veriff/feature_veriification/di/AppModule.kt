package com.example.veriff.feature_veriification.di

import com.example.veriff.feature_veriification.data.repository.VerifyRepositoryImpl
import com.example.veriff.feature_veriification.domain.repository.VerificationRepository
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.Module
import dagger.Provides

@Module
internal open class AppModule {
    @Provides
    open fun provideTextRecogniser(): TextRecognizer {
        return TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    }

    @Provides
    open fun provideFaceDector(): FaceDetector {
        return FaceDetection.getClient()
    }

    @Provides
    open fun provideRepository(faceDetector: FaceDetector, textRecognizer: TextRecognizer): VerificationRepository {
        return VerifyRepositoryImpl(textRecognizer, faceDetector)
    }
}
