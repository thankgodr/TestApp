package com.example.veriff.feature_veriification.di

import com.example.veriff.feature_veriification.domain.usecase.GetIDDetails
import com.example.veriff.feature_veriification.domain.usecase.VerifyFaceImageUsecase
import dagger.Component

@Component(modules = [AppModule::class])
interface UsecaseComponent {
    fun getUsecase(): GetIDDetails
    fun getVerifyUseCase(): VerifyFaceImageUsecase
}


