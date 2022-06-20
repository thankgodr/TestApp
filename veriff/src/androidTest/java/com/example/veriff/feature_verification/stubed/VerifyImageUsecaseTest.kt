package com.example.veriff.feature_verification.stubed

import android.graphics.Bitmap
import com.example.veriff.core.Resource
import com.example.veriff.feature_verification.di.FakeAppModule
import com.example.veriff.feature_veriification.di.DaggerUsecaseComponent
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Test

class VerifyImageUsecaseTest {
    // Arrange
    val testingComponent = DaggerUsecaseComponent.builder()
        .appModule(FakeAppModule()).build()

    // Act
    val verifyUseCase = testingComponent.getVerifyUseCase()

    // Assert
    @Test
    fun `test_that_the_repository_for_mage_verification_emits_loading_first`() {
        runBlocking {
            val res = verifyUseCase(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
                .first()
            Truth.assertThat(res).isInstanceOf(Resource.Loading::class.java)
        }
    }

    // Assert
    @Test
    fun `test_the_repository_for_image_verification_does_not_returns_null`() {
        runBlocking {
            val res = verifyUseCase(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
                .last()
            Truth.assertThat(res.data).isNotNull()
        }
    }

    // Assert
    @Test
    fun `test_that_repository_for_mage_verification_returns_class_of_Bitmap`() {
        runBlocking {
            val res = verifyUseCase(Bitmap.createBitmap(1, 1, Bitmap.Config.ALPHA_8))
                .last()
            Truth.assertThat(res.data).isInstanceOf(Bitmap::class.java)
        }
    }
}
